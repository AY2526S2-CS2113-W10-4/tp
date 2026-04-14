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

    public void save(InternshipList internshipList) {
        try {
            File f = new File(filePath);
            File parentDir = f.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
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

                if (parts.length != 3) {
                    errorLog.append("Warning: Corrupted format in internships.txt. Skipping and cleaning: [")
                            .append(line).append("]\n");
                    continue;
                }

                String title = parts[0].trim();
                String company = parts[1].trim();
                String status = parts[2].trim().toUpperCase();

                // Check for strings less than 2 characters
                if (title.length() < 2 || company.length() < 2) {
                    errorLog.append("Warning: Title or company name must be >= 2 chars. Skipping and cleaning: [")
                            .append(line).append("]\n");
                    continue;
                }

                // Duplicate Check
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

                Internship loadedInternship = new Internship(title, company);

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

        if (errorLog.length() > 0) {
            new Ui().print(errorLog.toString().trim());
        }

        return loadedList;
    }
}
