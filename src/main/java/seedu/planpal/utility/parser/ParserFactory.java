package seedu.planpal.utility.parser;

import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.ExpenseManager;

public class ParserFactory {

    private static final String CONTACT_FILE = "contacts.txt";
    private static final String EXPENSE_FILE = "expenses.txt";
    private static final String ACTIVITY_FILE = "activities.txt";

    public static <T>  Parser getParser(String fileName, T manager) throws PlanPalExceptions {
        switch (fileName) {
        case CONTACT_FILE:
            return new ContactParser((ContactManager)manager);

        case EXPENSE_FILE:
            return new ExpenseParser((ExpenseManager)manager);

        case ACTIVITY_FILE:
            return new ActivityParser((ActivityManager)manager);

        default:
            throw new PlanPalExceptions("Unknown file type: " + fileName);
        }
    }
}
