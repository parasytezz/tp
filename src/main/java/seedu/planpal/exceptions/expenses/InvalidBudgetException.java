package seedu.planpal.exceptions.expenses;

/**
 * Represents an exception that is thrown when an invalid budget value is provided.
 */
public class InvalidBudgetException extends RuntimeException {
    /**
     * Constructs a new InvalidBudgetException with a default error message.
     */
    public InvalidBudgetException() {
        super("The budget value cannot be evaluated! Make sure it is a double type!");
    }
}
