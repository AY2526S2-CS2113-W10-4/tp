package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.undo.OperationSnapshot;

public class UndoCommand extends CommandClass{

    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_KEYWORD = "undo";
    private final Executor executor;
    private final InternshipList internshipList;
    private final InterviewList interviewList;
    private final OperationSnapshot operationSnapshot;


    public UndoCommand(Parser parser, Executor executor, InternshipList internshipList,
                       InterviewList interviewList, OperationSnapshot operationSnapshot) {
        super(parser);
        this.executor = executor;
        this.internshipList = internshipList;
        this.interviewList = interviewList;
        this.operationSnapshot = operationSnapshot;
    }

    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassException("Error: expecting no variable");
        }

        executor.setAliasMap(operationSnapshot.getAliasMapCopy());
        internshipList.setInternships(operationSnapshot.getInternshipListCopy().getInternships());
        interviewList.setInterviews(operationSnapshot.getInterviewListCopy().getInterviews());
    }
}
