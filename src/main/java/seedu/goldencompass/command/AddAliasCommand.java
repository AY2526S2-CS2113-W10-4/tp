package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.exception.GoldenCompassTooManyArgumentsException;

import java.util.List;
import java.util.Map;

import static seedu.goldencompass.preparser.Config.DEFAULT_FLAG;

public class AddAliasCommand implements Executable{

    private static final int COMMAND_WORD_INDEX = 0;
    private static final int ALIAS_INDEX = 1;
    private static final int PARAM_LENGTH = 2;

    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {
        //default flag can only have one element
        String[] params = flagToParamMap.get(DEFAULT_FLAG).get(0).split("\\s+");

        if(params.length != PARAM_LENGTH) {
            throw new GoldenCompassTooManyArgumentsException(PARAM_LENGTH);
        }

        String commandWord = params[COMMAND_WORD_INDEX];
        String alias = params[ALIAS_INDEX];

        Executable executable = CommandRegistry.getCommand(commandWord);
        CommandRegistry.registerCommand(alias, executable);
    }
}
