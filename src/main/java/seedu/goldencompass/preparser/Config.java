package seedu.goldencompass.preparser;

import java.util.*;

public class Config {
    public static final Set<String> ALL_FLAGS = new HashSet<>(Arrays.asList("/a", "/b", "/c"));
    public static final Set<String> ALL_COMMANDS = new HashSet<>(Arrays.asList("example"));
    public static final int COMMAND_WORD_INDEX = 0;
    public static final String FLAG_INDICATOR = "/";
    public static final String DEFAULT_FLAG = "/default";

    public static final Map<String, Set<String>> COMMAND_TO_FLAGS_MAP = new HashMap<>();

    /**
     * Registers an array of flags to a set that contains all flags of the app.
     * <P>
     *     A flag should start with {@code /}, this method does not check
     *     if the entry is valid.
     * </P>
     * <P>
     *     Dulplicated flag entry is ignored.
     * </P>
     * @param flags a string array
     */
    public static void registerFlag(String... flags) {
        for(String flag : flags) {
            ALL_FLAGS.add(flag);
        }
        
    }

    private static void registerSystemFlag(Set<String> flags) {
        ALL_FLAGS.addAll(flags);
    }

    public static void registerCommandFlag(String commandKeyword, Set<String> commandFlags) {
        commandFlags.add(DEFAULT_FLAG); //because every command implicitly has a default flag
        registerSystemFlag(commandFlags);
        COMMAND_TO_FLAGS_MAP.put(commandKeyword, commandFlags);
        ALL_FLAGS.addAll(commandFlags);
    }

    public static Set<String> getCommandFlags(String commandKeyword) {
        return COMMAND_TO_FLAGS_MAP.get(commandKeyword);
    }
}
