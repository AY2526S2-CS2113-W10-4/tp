package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.util.List;

/**
 * Represents a command to list all internship applications in the system.
 * This command displays a numbered list of all internships with their company
 * names and job titles.
 */
public class ListCommand extends CommandClass {

    /** Reference to the internship list to display */
    private final InternshipList internshipList;

    /**
     * Constructs a ListCommand with the specified internship list.
     *
     * @param internshipList The list of internships to be displayed
     */
    public ListCommand(InternshipList internshipList) {
        this.internshipList = internshipList;
    }

    /**
     * Displays all internships in the list with their index numbers.
     * Shows company name and job title for each internship.
     * If the list is empty, displays a message indicating no internships.
     *
     * Checks for valid flags and displays all internships in the list.
     *
     * @throws GoldenCompassException if invalid flags are provided
     */
    @Override
    public void execute() throws GoldenCompassException {

        List<Internship> internships = internshipList.getInternships();

        if (internships.isEmpty()) {
            ui.print("No internships in the list.");
            return;
        }

        ui.print("Here are the internships you have added:");

        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            ui.print((i + 1) + ". " + intern.getCompanyName() + " - " + intern.getTitle());
        }

    }
}
