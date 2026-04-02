package seedu.goldencompass.internship;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Interview {

    protected Internship internship;
    protected LocalDateTime date;

    public Interview(Internship internship, LocalDateTime date) {
        assert internship != null : "Internship should not be null";
        assert date != null : "Date should not be null when provided";
        this.internship = internship;
        this.date = date;
    }

    public Interview(Internship internship) {
        assert internship != null : "Internship should not be null";
        this.internship = internship;
        this.date = null;
    }

    /**
     * Sets the deadline date and time of this interview.
     * @param date a {@code LocalDateTime} representing the new deadline.
     */
    public void setDate(LocalDateTime date) {
        assert date != null : "Date should not be null";
        this.date = date;
    }

    /**
     * Returns the deadline date and time of this interview.
     * @return the deadline as a {@code LocalDateTime}.
     */
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        if (date == null) {
            return internship.toString() + " @ No date set";
        }
        return internship.toString() + " @ " + date.toLocalDate() + " " + date.toLocalTime();
    }

    public Internship getInternship() {
        return this.internship;
    }

    /**
     * Returns {@code true} if this interview matches all non-null criteria.
     * Text matching is case-insensitive substring matching.
     *
     * @param company the company name to match, or {@code null} to skip.
     * @param title   the role/title to match, or {@code null} to skip.
     * @param date    the date to match exactly, or {@code null} to skip.
     * @return {@code true} if all non-null criteria match.
     */
    public boolean matches(String company, String title, LocalDate date) {
        if (company != null
                && !internship.companyName.toLowerCase().contains(company.toLowerCase())) {
            return false;
        }
        if (title != null
                && !internship.title.toLowerCase().contains(title.toLowerCase())) {
            return false;
        }
        if (date != null && (this.date == null || !this.date.toLocalDate().equals(date))) {
            return false;
        }
        return true;
    }

}
