package seedu.goldencompass.internship;

public class Internship {

    protected String title;
    protected String companyName;
    protected String comments;
    protected String link;
    protected boolean hasApplied;
    protected boolean hasReceivedOffer;
    protected Interview interview;

    public Internship(String title, String companyName) {
        this.title = title;
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return title + " at " + companyName;
    }

}
