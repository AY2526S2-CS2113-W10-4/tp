package seedu.goldencompass.storage;

import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InternshipStorage {
    private static final Logger logger = Logger.getLogger(InternshipStorage.class.getName());
    private final String filePath;

    public InternshipStorage(String filePath) {
        assert filePath != null : "File path cannot be null";
        this.filePath = filePath;
    }

    /**
     * Saves the current list of internships to the hard drive.
     */
    public void save(InternshipList internshipList) {
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);

            for (Internship intern : internshipList.getInternships()) {
                String status = "PENDING";
                if (intern.hasOffer()) {
                    status = "OFFER";
                } else if (intern.isRejected()) {
                    status = "REJECTED";
                }

                fw.write(intern.getTitle() + " | " + intern.getCompanyName() + " | " + status + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving internships", e);
        }
    }

    /**
     * Loads the internships from the text file into an ArrayList.
     * Prevents duplicates, handles legacy formats, and skips corrupted lines.
     */
    public ArrayList<Internship> load() {
        ArrayList<Internship> loadedList = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            return loadedList;
        }

        StringBuilder errorLog = new StringBuilder();

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s*\\|\\s*");

                // 1. Strict length check (supports legacy 2-column format)
                if (parts.length < 2) {
                    errorLog.append("Error: Corrupted format in internships.txt. Skipping: [")
                            .append(line).append("]\n");
                    continue;
                }

                String title = parts[0].trim();
                String company = parts[1].trim();

                // 2. Strict length validation (Must be at least 2 characters)
                if (title.length() < 2 || company.length() < 2) {
                    errorLog.append("Error: Title or company name too short (must be >= 2 chars). Skipping: [")
                            .append(line).append("]\n");
                    continue;
                }

                // 3. Duplicate Check: Prevent crashes from manual file edits
                boolean isDuplicate = false;
                for (Internship existing : loadedList) {
                    if (existing.getTitle().equals(title) && existing.getCompanyName().equals(company)) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate) {
                    errorLog.append("Warning: Duplicate internship for ").append(company)
                            .append(" (").append(title).append(") skipped and cleaned.\n");
                    continue;
                }

                // 4. Create the internship
                Internship loadedInternship = new Internship(title, company);

                // 5. Update the status (Defaults to PENDING for legacy files)
                String status = (parts.length >= 3) ? parts[2].trim().toUpperCase() : "PENDING";

                switch (status) {
                case "OFFER":
                    loadedInternship.markAsOffer();
                    break;
                case "REJECTED":
                    loadedInternship.markAsRejected();
                    break;
                case "PENDING":
                    break;
                default:
                    errorLog.append("Warning: Unknown status '").append(status)
                            .append("' for ").append(company).append(". Defaulting to PENDING.\n");
                    break;
                }

                loadedList.add(loadedInternship);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not load internships from " + filePath);
        }

        // 6. Print all errors and warnings at once to the UI
        if (errorLog.length() > 0) {
            new Ui().print(errorLog.toString().trim());
        }

        return loadedList;
    }
}