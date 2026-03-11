package seedu.goldencompass.command;

import java.util.List;
import java.util.Map;


public class ExampleCommand implements Executable{

    /**
     * Prints a list of legal flags with their respective parameters.
     * <P><B>Example Use</B></P>
     * @param flagToParamMap
     */
    @Override
    public void execute(Map<String, List<String>> flagToParamMap) {
        System.out.println("hi, I am ExampleCommand");
        for(String key : flagToParamMap.keySet()) {
            List<String> params = flagToParamMap.get(key);
            System.out.println("I have this flag " + key + " with params: " + params);
        }
    }
}
