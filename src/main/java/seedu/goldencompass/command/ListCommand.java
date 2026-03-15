package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.preparser.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListCommand implements Executable {

    private static final ArrayList<String> FLAGS = new ArrayList<>(Arrays.asList("/list"));
    private InternshipList internshipList;

    public ListCommand(InternshipList internshipList) {
        this.internshipList = internshipList;
        Config.registerFlag(FLAGS.toArray(new String[0]));
    }

    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {
        checkFlag(flagToParamMap, FLAGS);
        internshipList.list(); // Call the list method we just added
    }
}
