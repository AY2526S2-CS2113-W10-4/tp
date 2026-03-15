package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.preparser.Config;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.ui.Ui; // Import the UI class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a command to add a new internship to the GoldenCompass tracker.
 */
public class AddInternshipCommand implements Executable {

    // 1. Roaring Cat says no need for DEFAULT_FLAG in the list, just the extra flags
    private static final ArrayList<String> FLAGS = new ArrayList<>(List.of("/t"));

    private final InternshipList internshipList;

    public AddInternshipCommand(InternshipList internshipList) {
        this.internshipList = internshipList;
        Config.registerFlag(FLAGS.toArray(new String[0]));
    }

    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {

        // 2. Trust their checkFlag; it already throws "missing flags" errors
        checkFlag(flagToParamMap, FLAGS);

        // 3. Extract logic: DEFAULT_FLAG is used for the company (the first part)
        // String.join handles multiple entries if the user repeats a flag
        String companyName = String.join(" ", getParamsOf(Config.DEFAULT_FLAG, flagToParamMap)).trim();
        String title = String.join(" ", getParamsOf("/t", flagToParamMap)).trim();

        if (companyName.isEmpty() || title.isEmpty()) {
            throw new GoldenCompassException("Company name and title cannot be empty!");
        }

        Internship newInternship = new Internship(title, companyName);
        internshipList.add(newInternship);

        // 4. Use Ui.print instead of System.out.println
        new Ui().print("Got it! I've added this internship to your compass:\n  " + newInternship);
    }
}
