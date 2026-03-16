package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddInternshipCommandTest {

    private Parser parser;
    private InternshipList internshipList;
    private AddInternshipCommand addInternshipCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        internshipList = new InternshipList();
        addInternshipCommand = new AddInternshipCommand(parser, internshipList);
    }

    @Test
    public void execute_validInput_addsInternshipSuccessfully() throws GoldenCompassException {

        parser.parse("add Shopee /t Software Engineer");

        addInternshipCommand.execute();

        assertEquals(1, internshipList.getSize());
    }

    @Test
    public void execute_emptyCompanyName_throwsException() throws GoldenCompassException {

        parser.parse("add     /t Software Engineer");

        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);

    }

    @Test
    public void execute_emptyTitle_throwsException() throws GoldenCompassException {

        parser.parse("add Shopee /t    ");

        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);

    }
}
