package seedu.planpal.utility.parser.modeparsers;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.modes.expenses.ExpenseModeFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.BackUpManager;
import seedu.planpal.utility.parser.Parser;

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
            String command = inputParts[0].toLowerCase();
            String description;

            switch (command) {
            case BUDGET_COMMAND:
                expenseManager.getBudgetManager().setBudget(inputParts[1].trim());
                return true;

            case ADD_COMMAND:
                description = inputParts[1].trim();
                if (description.contains(ExpenseModeFunctions.RECURRING_TAG)) {
                    expenseManager
                            .getRecurringManager()
                            .addRecurringExpense(ExpenseModeFunctions.removeRecurringTag(description));
                    return true;
                }
                expenseManager.addExpense(inputParts[1].trim());
                return true;

            case LIST_COMMAND:
                try {
                    expenseManager.viewExpenseList(inputParts[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    expenseManager.viewExpenseList();
                }
                return true;

            case EDIT_COMMAND:
                try {
                    expenseManager.editExpense(inputParts[1].trim());
                } catch (NumberFormatException e) {
                    throw new PlanPalExceptions("Invalid index format. Please provide a valid number.");
                }
                return true;

            case DELETE_COMMAND:
                expenseManager.deleteExpense(inputParts[1].trim());
                return true;

            case FIND_COMMAND:
                expenseManager.findExpense(inputParts[1].trim());
                return true;

            case EXIT_MODE_COMMAND:
                break;

            case CLEAR_COMMAND:
                Ui.printLine();
                Ui.clearScreen();
                return true;

            case BYE_COMMAND:
                Ui.printByeMessage();
                System.exit(0);
                break;

            case BACK_UP_COMMAND:
                BackUpManager.backupData();
                Ui.print("Backup Complete!");
                return true;

            case RESTORE_COMMAND:
                BackUpManager.restoreData();
                Ui.print("Restore Complete! Now re-enter into your mode!");
                return false;

            default:
                throw new IllegalCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EmptyDescriptionException();
        }
        return false;
    }
}
