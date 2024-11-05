package seedu.planpal.modes.expenses;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.filemanager.Storeable;

public class RecurringExpense extends Expense implements Editable, Storeable {

    public RecurringExpense(String description) throws PlanPalExceptions {
        super(description);
        setCommandDescription(ExpenseModeFunctions.RECURRING_TAG + " " + description);
    }

    @Override
    public String getStoragePath() {
        return "./data/expenses/expenses_recurring.txt";
    }

}
