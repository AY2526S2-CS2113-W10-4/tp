package seedu.goldencompass.command;

import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.preparser.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddInternshipCommandTest {

    @Test
    public void execute_validInput_addsInternshipSuccessfully() throws GoldenCompassException {
        // 1. Setup the environment
        InternshipList internships = new InternshipList();
        AddInternshipCommand addCommand = new AddInternshipCommand(internships);

        // 2. Simulate what the Preparser does for: add Shopee /t Software Engineer
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put(Config.DEFAULT_FLAG, List.of("Shopee"));
        parameters.put("/t", List.of("Software Engineer"));

        // 3. Execute the command
        addCommand.execute(parameters);

        // 4. Verify the result (List should now have 1 item)
        assertEquals(1, internships.getSize());
    }

    @Test
    public void execute_emptyCompanyName_throwsException() {
        InternshipList internships = new InternshipList();
        AddInternshipCommand addCommand = new AddInternshipCommand(internships);

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put(Config.DEFAULT_FLAG, List.of("   ")); // Simulating empty/blank company
        parameters.put("/t", List.of("Software Engineer"));

        // Verify that executing this throws the expected exception
        assertThrows(GoldenCompassException.class, () -> addCommand.execute(parameters));
    }

    @Test
    public void execute_emptyTitle_throwsException() {
        InternshipList internships = new InternshipList();
        AddInternshipCommand addCommand = new AddInternshipCommand(internships);

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put(Config.DEFAULT_FLAG, List.of("Shopee"));
        parameters.put("/t", List.of("   ")); // Simulating empty/blank title

        // Verify that executing this throws the expected exception
        assertThrows(GoldenCompassException.class, () -> addCommand.execute(parameters));
    }
}
