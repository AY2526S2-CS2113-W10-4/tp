package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationSnapshot;

public interface DataHistory {
    default void modifyDataWith(OperationSnapshot operationSnapshot, Executor executor,
                                InternshipList internshipList, InterviewList interviewList) {
        executor.setAliasMap(operationSnapshot.getAliasMapCopy());
        internshipList.setInternships(operationSnapshot.getInternshipListCopy().getInternships());
        interviewList.setInterviews(operationSnapshot.getInterviewListCopy().getInterviews());
    }
}
