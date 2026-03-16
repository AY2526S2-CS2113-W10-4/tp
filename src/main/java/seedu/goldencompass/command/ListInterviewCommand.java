package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;

import java.util.List;

public class ListInterviewCommand extends CommandClass {

    private final InterviewList interviewList;

    public ListInterviewCommand(InterviewList interviewList) {
        this.interviewList = interviewList;
    }

    @Override
    public void execute() throws GoldenCompassException {

        List<Interview> interviews = interviewList.getInterviews();

        if (interviews.isEmpty()) {
            ui.print("You don't have any interviews!");
            return;
        }

        ui.print("Here are the internships you have added:");

        interviews.forEach(x -> ui.print(x.toString()));

    }

}
