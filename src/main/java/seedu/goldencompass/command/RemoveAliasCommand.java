package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;

public class RemoveAliasCommand extends CommandClass{
    //default + /a
    private static final int PARAM_LENGTH = 2;

    private final Executor executor;

    public RemoveAliasCommand(Parser parser, Executor executor) {
        super(parser);
        this.executor = executor;
    }

    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassException("Error: Expecting only one argument.");
        }
        String alias = parser.getParamsOf("/a").get(0);

        executor.removeAlias(alias);
        ui.print("Alias: \"" + alias + "\" is now removed.");
    }
}
