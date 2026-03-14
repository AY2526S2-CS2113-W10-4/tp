package seedu.goldencompass.preparser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Config {
    public static final Set<String> ALL_FLAGS = new HashSet<>(Arrays.asList("/a", "/b", "/c"));
    public static final Set<String> ALL_COMMANDS = new HashSet<>(Arrays.asList("example"));
    public static final int COMMAND_WORD_INDEX = 0;
    public static final String FLAG_INDICATOR = "/";
    public static final String DEFAULT_FLAG = "/default";

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
}
