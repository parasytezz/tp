package seedu.planpal.utility.parser;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.ExpenseManager;
import seedu.planpal.utility.Ui;

/**
 * Parses user commands and delegates them to the appropriate methods in {@link ExpenseManager}.
 */
public class ExpenseParser extends Parser {
    private static final int INPUT_SEGMENTS = 2;
    private static final String BUDGET_COMMAND = "budget";
    private ExpenseManager expenseManager;

    /**
     * Constructs an ExpenseParser with a given ExpenseManager.
     *
     * @param expenseManager The manager that handles expense-related operations.
     */
    public ExpenseParser(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    /**
     * Processes the user's input command and delegates the action to the appropriate method.
     *
     * @param input The command input from the user.
     * @return true if the command is successfully executed, false otherwise.
     * @throws PlanPalExceptions If there is any issue processing the command
     */
    @Override
    public boolean processCommand(String input) throws PlanPalExceptions {
        try {
            String[] inputParts = input.split(" ", INPUT_SEGMENTS);
            String command = inputParts[0];
            String description;

            switch (command) {
            case BUDGET_COMMAND:
                expenseManager.setBudget(inputParts[1].trim());
                return true;

            case Parser.ADD_COMMAND:
                description = inputParts[1].trim();
                expenseManager.addExpense(description);
                return true;

            case Parser.LIST_COMMAND:
                expenseManager.viewExpenseList();
                return true;

            case Parser.DELETE_COMMAND:
                expenseManager.deleteExpense(inputParts[1].trim());
                return true;

            case Parser.FIND_COMMAND:
                description = inputParts[1].trim();
                expenseManager.findExpense(description);
                return true;

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
