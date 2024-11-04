package seedu.planpal.exceptions;

/**
 * Represents an exception that is thrown when an invalid index is provided.
 */
public class InvalidIndexException extends PlanPalExceptions {
    /**
     * Constructs a new InvalidIndexException with a default error message.
     */
    public InvalidIndexException() {
        super("Invalid Index!");
    }
}
