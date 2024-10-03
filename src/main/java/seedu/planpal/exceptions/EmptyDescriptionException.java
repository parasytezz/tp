package seedu.planpal.exceptions;

/**
 * Represents an exception that is thrown when a description segment is empty.
 * It extends the UranusExceptions class and provides a specific error message.
 */
public class EmptyDescriptionException extends PlanPalExceptions {

    /**
     * Constructs a new EmptyDescriptionException.
     * This exception is triggered when the task description provided by the user is empty.
     */
    public EmptyDescriptionException() {
        super();
    }

    /**
     * Overrides the getMessage method to provide a specific
     * error message for empty descriptions.
     *
     * @return A string message indicating that the description cannot be empty.
     */
    @Override
    public String getMessage() {
        return "Description cannot be empty!";
    }
}
