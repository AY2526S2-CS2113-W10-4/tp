package seedu.goldencompass.internship;

public class Interview {

    protected Internship internship;
    protected String date;
    protected String comments;

    public Interview(Internship internship, String date) {
        this.internship = internship;
        this.date = date;
    }

    /**
     * Sets the deadline date of this interview.
     * @param date a string representing the new deadline date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the deadline date of this interview.
     * @return the deadline date as a string.
     */
    public String getDate() {
        return date;
    }

}
