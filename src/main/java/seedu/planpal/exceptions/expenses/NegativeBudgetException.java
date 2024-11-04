package seedu.planpal.exceptions.expenses;

import seedu.planpal.exceptions.PlanPalExceptions;

/**
 * Represents an exception that is thrown when a negative budget value is provided.
 */
public class NegativeBudgetException extends PlanPalExceptions {

    /**
     * Constructs a new NegativeBudgetException with a default error message.
     */
    public NegativeBudgetException() {
        super("Budget cannot be negative!");
    }
}
