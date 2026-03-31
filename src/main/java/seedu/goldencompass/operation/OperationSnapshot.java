package seedu.goldencompass.operation;


import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;

import java.util.HashMap;
import java.util.Map;

public class OperationSnapshot {
    //TODO change to CommandClass
    //Command command;
    private InternshipList internshipListCopy;
    private InterviewList interviewListCopy;
    private Map<String, String> aliasMapCopy;

    public OperationSnapshot() {
        this.internshipListCopy = new InternshipList();
        this.interviewListCopy = new InterviewList();
        this.aliasMapCopy = new HashMap<>();
    }

    public OperationSnapshot(OperationSnapshot other) {
        snapshot(other.internshipListCopy, other.interviewListCopy, other.aliasMapCopy);
    }

    public void snapshot(InternshipList internshipList, InterviewList interviewList, Map<String, String> aliasMap) {
        this.internshipListCopy = new InternshipList(internshipList);
        this.interviewListCopy = new InterviewList(interviewList);
        this.aliasMapCopy = new HashMap<>(aliasMap);
    }
    public void snapshot(OperationSnapshot other) {
        snapshot(other.internshipListCopy, other.interviewListCopy, other.aliasMapCopy);
    }
    public InternshipList getInternshipListCopy() {
        return internshipListCopy;
    }

    public InterviewList getInterviewListCopy() {
        return interviewListCopy;
    }

    public Map<String, String> getAliasMapCopy() {
        return aliasMapCopy;
    }
}
