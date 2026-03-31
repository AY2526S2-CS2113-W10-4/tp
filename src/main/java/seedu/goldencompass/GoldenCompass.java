package seedu.goldencompass;

import seedu.goldencompass.command.Executor;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.storage.AliasStorage;
import seedu.goldencompass.ui.Ui;
import seedu.goldencompass.storage.InternshipStorage;
import seedu.goldencompass.storage.InterviewStorage;
import seedu.goldencompass.undo.OperationSnapshot;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoldenCompass {
    /**
     * Main entry-point for the GoldenCompass application.
     */
    private final Ui ui;
    private final Parser parser;
    private final InternshipList internships;
    private final InterviewList interviews;
    private final Executor executor;
    private final InternshipStorage internshipStorage;
    private final InterviewStorage interviewStorage;
    private final AliasStorage aliasStorage;
    private final OperationSnapshot oldOperationSnapshot;
    private final OperationSnapshot newOperationSnapshot;

    public GoldenCompass() throws GoldenCompassException {
        ui = new Ui();
        parser = new Parser();
        internshipStorage = new InternshipStorage("data/internships.txt");
        internships = new InternshipList(internshipStorage.load());
        internships.setUi(ui);
        this.interviewStorage = new InterviewStorage("data/interviews.txt");
        this.interviews = new InterviewList();
        this.oldOperationSnapshot = new OperationSnapshot();
        this.newOperationSnapshot = new OperationSnapshot();
        executor = new Executor(parser, internships, interviews, oldOperationSnapshot);
        this.interviewStorage.load(interviews, internships);
        this.aliasStorage = new AliasStorage("data/aliases.txt");
        this.aliasStorage.load(executor.getAliasMap());
    }

    public static void main(String[] args) throws IOException, GoldenCompassException {

        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.WARNING);

        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.WARNING);
        }
        new GoldenCompass().run();

    }

    public void run() {

        ui.greet();
        oldOperationSnapshot.snapshot(internships, interviews, executor.getAliasMap());
        newOperationSnapshot.snapshot(internships, interviews, executor.getAliasMap());//initialise


        while (true) {
            try {
                parser.parse(ui.read());
                if (parser.getCommand().equals("bye")) {
                    break;
                }
                executor.execute();
                if (!parser.getCommand().equals("list")) {
                    oldOperationSnapshot.snapshot(newOperationSnapshot);
                    newOperationSnapshot.snapshot(internships, interviews, executor.getAliasMap());
                }
                internshipStorage.save(internships);
                interviewStorage.save(interviews);
                aliasStorage.save(executor.getAliasMap());
            } catch (GoldenCompassException e) {
                ui.print(e.getMessage());
            }
        }

    }

}
