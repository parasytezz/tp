package seedu.planpal.utility.parser;

import seedu.planpal.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.expenses.ExpenseManager;

public class ParserFactory {
    public static <T>  Parser getParser(String fileName, T manager) throws PlanPalExceptions {
        switch (fileName) {
        case "contacts.txt":
            return new ContactParser((ContactManager)manager);

        case "expenses.txt":
            return new ExpenseParser((ExpenseManager)manager);

        default:
            throw new PlanPalExceptions("Unknown file type: " + fileName);
        }
    }
}
