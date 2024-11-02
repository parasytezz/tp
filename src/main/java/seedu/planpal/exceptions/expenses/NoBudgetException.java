package seedu.planpal.exceptions.expenses;

import seedu.planpal.exceptions.PlanPalExceptions;

/**
 * Represents an exception that is thrown when a budget has not been set.
 */
public class NoBudgetException extends PlanPalExceptions {

    /**
     * Constructs a new NoBudgetException with a specific error message.
     */
    public NoBudgetException() {
        super("You have not set your budget! \n" +
                "To do so, type: budget <value> \n" +
                "Example: budget 100");
    }
}

