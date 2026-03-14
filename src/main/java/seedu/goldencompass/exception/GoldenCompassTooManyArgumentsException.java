package seedu.goldencompass.exception;

public class GoldenCompassTooManyArgumentsException extends GoldenCompassException {
    public GoldenCompassTooManyArgumentsException(int expectedArgumentAmount) {
        super("Error: Too many arguments, expecting: " + expectedArgumentAmount);
    }
}
