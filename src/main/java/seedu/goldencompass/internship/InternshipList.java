package seedu.goldencompass.internship;

import seedu.goldencompass.ui.Ui;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of internship applications.
 * Provides functionality to add internships and display them.
 */
public class InternshipList {

    /** Internal list to store internship objects */
    private final List<Internship> internships = new ArrayList<>();

    public List<Internship> getInternships() {
        return internships;
    }

    /**
     * Adds a new internship to the list.
     *
     * @param x The internship object to be added
     */
    public void add(Internship x) {

        internships.add(x);
    }

    /**
     * Returns the number of internships in the list.
     *
     * @return The size of the internship list
     */
    public int getSize() {
        return internships.size();
    }

}
