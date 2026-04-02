package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;

public class RemoveAliasCommand extends Command {
    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_DESCRIPTION = """
            This command can remove an alias.
            Format: remove-alias ALIAS
            """;
    private static final String FLAG_DESCRIPTION = """
            Flags:
            This command only takes one parameter, no flag required.
            """;

    private final Executor executor;

    public RemoveAliasCommand(Parser parser, Executor executor) {
        super(parser);
        this.executor = executor;
    }

    @Override
    public void execute() throws GoldenCompassException {
        if(checkHelpFlag(COMMAND_DESCRIPTION, FLAG_DESCRIPTION)) {
            return;
        }

        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassException("Error: Expecting only one argument.");
        }

        String alias = parser.getDefaultParam();

        executor.removeAlias(alias);
        ui.print("Alias: \"" + alias + "\" is now removed.");
    }
}
