package seedu.planpal.utility.parser;

import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;
import seedu.planpal.utility.parser.modeparsers.ContactParser;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

public class ParserFactory {

    private static final String CONTACT_FILE = "contacts";
    private static final String EXPENSE_FILE = "expenses";
    private static final String ACTIVITY_FILE = "activities";

    public static <T>  Parser getParser(String fileName, T manager) throws PlanPalExceptions {
        if (fileName.startsWith(CONTACT_FILE)) {
            return new ContactParser((ContactManager) manager);
        } else if (fileName.startsWith(EXPENSE_FILE)) {
            return new ExpenseParser((ExpenseManager) manager);
        } else if (fileName.startsWith(ACTIVITY_FILE)) {
            return new ActivityParser((ActivityManager) manager);
        } else {
            throw new PlanPalExceptions("Unknown file type: " + fileName);
        }
    }
}
