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
        super("Either you have not set your budget or there is an invalid syntax! \n" +
                "To set your budget, type: budget <value> /month: <monthValue> \n" +
                "Example: budget 100 /month: 2024-06");
    }
}

