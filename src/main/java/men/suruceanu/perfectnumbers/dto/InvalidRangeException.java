package men.suruceanu.perfectnumbers.dto;

public class InvalidRangeException extends Exception {

    public InvalidRangeException() {
        super("You have entered an invalid range. Please make sure the range is valid");
    }

    public InvalidRangeException(String message) {
        super(message);
    }
}
