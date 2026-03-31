package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.exception.GoldenCompassTooManyArgumentsException;
import seedu.goldencompass.parser.Parser;


public class AddAliasCommand extends Command {

    //default + /c + /a; total 3
    private static final int PARAM_LENGTH = 3;

    private final Executor executor;
    public AddAliasCommand(Parser parser, Executor executor) {
        super(parser);
        this.executor = executor;
    }


    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassTooManyArgumentsException(PARAM_LENGTH);
        }
        String commandWord = parser.getParamsOf("/c").get(0);
        String alias = parser.getParamsOf("/a").get(0);

        executor.addAlias(commandWord, alias);
        ui.print("Command: \"" + commandWord + "\" now has a new alias: \"" + alias + "\"");
    }
}
