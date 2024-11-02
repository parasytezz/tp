package seedu.planpal.exceptions;

/**
 * Represents an exception that is thrown when an invalid mode is selected.
 * It extends the PlanPalExceptions class and provides a specific error message.
 */
public class InvalidModeException extends PlanPalExceptions {

    /**
     * Constructs a new InvalidModeException.
     * This exception is triggered when the user selects a mode that does not exist or is not valid.
     */
    public InvalidModeException() {
        super();
    }

    /**
     * Overrides the getMessage method to provide a specific
     * error message for invalid mode selection.
     *
     * @return A string message indicating that the selected mode is invalid.
     */
    @Override
    public String getMessage() {
        return "Invalid mode selected! Please select a valid mode.";
    }
}
