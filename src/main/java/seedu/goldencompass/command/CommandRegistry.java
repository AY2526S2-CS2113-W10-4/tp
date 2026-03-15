package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private static Map<String, Executable> executableMap = new HashMap<>();

    /**
     * Associates a command extending {@code Executable} with a {@code commandWord} into a Map.
     * @param commandWord a string, the key.
     * @param command a command extending {@code Executable}, the value.
     * @see Executable
     */
    public static void registerCommand(String commandWord, Executable command) {
        executableMap.put(commandWord, command);
    }

    /**
     * Returns an executable that associates with a {@code commandWord}.
     * @param commandWord a string
     * @return an executable that associates with a {@code commandWord}.
     */
    public static Executable getCommand(String commandWord) throws GoldenCompassException {
        Executable executable = executableMap.get(commandWord);

        //cannot find an executable given the command word
        if(executable == null) {
            throw new GoldenCompassException("Error: Unknown command: " + commandWord);
        }

        return executable;
    }
}
