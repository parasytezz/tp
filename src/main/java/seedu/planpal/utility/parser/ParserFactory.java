package seedu.planpal.utility.parser;

import seedu.planpal.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.expenses.ExpenseManager;

public class ParserFactory {

    private static final String CONTACT_FILE = "contacts.txt";
    private static final String EXPENSE_FILE = "expenses.txt";

    public static <T>  Parser getParser(String fileName, T manager) throws PlanPalExceptions {
        switch (fileName) {
        case CONTACT_FILE:
            return new ContactParser((ContactManager)manager);

        case EXPENSE_FILE:
            return new ExpenseParser((ExpenseManager)manager);

        default:
            throw new PlanPalExceptions("Unknown file type: " + fileName);
        }
    }
}
