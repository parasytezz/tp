package seedu.planpal.utility.parser;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.expenses.ExpenseManager;
import seedu.planpal.utility.Ui;

public class ExpenseParser extends Parser {
    private static final int INPUT_SEGMENTS = 2;
    ExpenseManager expenseManager;

    public ExpenseParser(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    @Override
    public boolean processCommand(String input) throws PlanPalExceptions {
        try {
            String[] inputParts = input.split(" ", INPUT_SEGMENTS);
            String command = inputParts[0];
            String description;

            switch (command) {
            case Parser.ADD_COMMAND:
                description = inputParts[1].trim();
                expenseManager.addExpense(description);
                return true;
                // fallthrough

            case Parser.LIST_COMMAND:
                expenseManager.viewExpenseList();
                return true;
                // fallthrough

            case Parser.EXIT_MODE_COMMAND:
                break;

            case Parser.BYE_COMMAND:
                Ui.printByeMessage();
                System.exit(0);
                break;

            default:
                throw new IllegalCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EmptyDescriptionException();
        }
        return false;
    }

}
