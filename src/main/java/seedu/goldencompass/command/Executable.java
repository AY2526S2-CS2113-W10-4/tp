package seedu.goldencompass.command;

import java.util.List;
import java.util.Map;

/**
 * All command should implement this interface in order to be executed.
 */
public interface Executable {
    void execute(Map<String, List<String>> flagToParamMap);

}
