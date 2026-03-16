package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

public class CommandClass implements Command {

    /** Package-private UI and parser */
    Ui ui;
    Parser parser;

    /** No-argument constructor, automatically called in the constructors of subclasses */
    public CommandClass() {
        ui = new Ui();
    }

    @Override
    public void execute() throws GoldenCompassException {

    }

}
