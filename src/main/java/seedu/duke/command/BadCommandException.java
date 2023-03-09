package seedu.duke.command;

public class BadCommandException extends Exception {
    public BadCommandException(String errorMessage) {
        super(errorMessage);
    }
}
