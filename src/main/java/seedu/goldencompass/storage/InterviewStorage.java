package seedu.goldencompass.storage;

import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterviewStorage {
    private static final Logger logger = Logger.getLogger(InterviewStorage.class.getName());
    private final String filePath;

    public InterviewStorage(String filePath) {
        assert filePath != null : "File path cannot be null";
        this.filePath = filePath;
    }

    /**
     * Saves interviews by storing the linked company name and the date.
     */
    public void save(InterviewList interviewList) {
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);

            for (Interview i : interviewList.getInterviews()) {
                String company = i.getInternship().getCompanyName();
                String role = i.getInternship().getTitle();
                String date = (i.getDate() != null) ? i.getDate().toString() : "null";

                fw.write(company + " | " + role + " | " + date + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving interviews", e);
        }
    }

    /**
     * Loads interviews and reconnects them to existing internships.
     */
    public void load(InterviewList interviewList, InternshipList internshipList) {
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }

        StringBuilder errorLog = new StringBuilder();

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length < 3) {
                    errorLog.append("Warning: Corrupted format in interviews.txt. Skipping and cleaning: [")
                            .append(line).append("]\n");
                    continue;
                }

                String companyName = parts[0];
                String role = parts[1];
                String dateStr = parts[2];

                Internship linkedInternship = internshipList.findInternshipByCompanyAndRole(companyName, role);

                if (linkedInternship != null) {
                    if (linkedInternship.getInterview() != null) {
                        errorLog.append("Warning: Duplicate interview for ").append(companyName)
                                .append(" (").append(role).append(") skipped and cleaned.\n");
                        continue;
                    }

                    // Strict date parsing
                    try {
                        LocalDateTime parsedDate = LocalDateTime.parse(dateStr);
                        Interview interview = new Interview(linkedInternship);
                        interview.setDate(parsedDate);
                        interviewList.add(interview);
                    } catch (DateTimeParseException e) {
                        errorLog.append("Warning: Date corrupted ('").append(dateStr)
                                .append("') for ").append(companyName)
                                .append(" (").append(role).append("). Skipping and cleaning.\n");
                    }
                } else {
                    errorLog.append("Warning: No matching internship found for ")
                            .append(companyName).append(" (").append(role)
                            .append("). Skipping and cleaning interview.\n");
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not load interviews from " + filePath);
        }

        if (errorLog.length() > 0) {
            new Ui().print(errorLog.toString().trim());
        }
    }
}
