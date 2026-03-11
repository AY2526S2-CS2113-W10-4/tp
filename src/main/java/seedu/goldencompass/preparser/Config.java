package seedu.goldencompass.preparser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Config {
    protected static final Set<String> ALL_FLAGS = new HashSet<>(Arrays.asList("/a", "/b", "/c"));
    protected static final Set<String> ALL_COMMANDS = new HashSet<>(Arrays.asList("example"));
    protected static final int COMMAND_WORD_INDEX = 0;
    protected static final String FLAG_INDICATOR = "/";
    protected static final String DEFAULT_FLAG = "/default";
}
