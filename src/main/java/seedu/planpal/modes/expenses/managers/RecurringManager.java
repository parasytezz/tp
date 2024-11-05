package seedu.planpal.modes.expenses.managers;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.Expense;
import seedu.planpal.modes.expenses.ExpenseModeFunctions;
import seedu.planpal.modes.expenses.RecurringExpense;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Manages recurring expenses.
 */
public class RecurringManager implements ExpenseModeFunctions, ListFunctions {
    FileManager savedExpenses;
    private ArrayList<RecurringExpense> recurringExpensesList = new ArrayList<>();

    /**
     * Constructs a RecurringManager with a FileManager for handling expense data storage.
     * @param fileManager The file manager to handle saving and loading of recurring expense data.
     */
    public RecurringManager(FileManager fileManager) {
        this.savedExpenses = fileManager;
    }

    /**
     * Retrieves the list of recurring expenses.
     * @return The list of recurring expenses.
     */
    public ArrayList<RecurringExpense> getRecurringExpensesList() {
        return recurringExpensesList;
    }

    /**
     * Adds recurring expenses for a specified month into the monthly expenses map.
     * @param newMonth The month to which the recurring expenses should be added.
     * @param monthlyExpenses The map of month keys to lists of expenses.
     * @throws PlanPalExceptions If the description for an expense is empty or otherwise invalid.
     */
    public void addRecurringToMonthlyExpenses(String newMonth, Map<String, ArrayList<Expense>> monthlyExpenses)
            throws PlanPalExceptions {
        if (monthlyExpenses.get(newMonth) != null){
            return;
        }
        PrintStream out = System.out;
        Ui.setDummyStream();
        monthlyExpenses.putIfAbsent(newMonth, new ArrayList<>());
        for (RecurringExpense recurringExpense : recurringExpensesList){
            String description =
                    recurringExpense.getCommandDescription()
                            .substring(ExpenseModeFunctions.RECURRING_TAG.length())
                            .trim();
            description += " " + MONTH_SEPARATOR + newMonth;
            Expense expense = new Expense(description);
            addToList(monthlyExpenses.get(newMonth), expense);
            savedExpenses.saveList(monthlyExpenses.get(newMonth));
        }
        Ui.setMainStream(out);
    }

    /**
     * Adds a new recurring expense to the list and saves it to the associated file.
     * @param description The description of the recurring expense to add.
     * @throws EmptyDescriptionException If the provided description is empty.
     */
    public void addRecurringExpense(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }
        RecurringExpense newExpense = new RecurringExpense(description);
        addToList(recurringExpensesList, newExpense);
        savedExpenses.saveList(recurringExpensesList);
    }

    public void viewRecurringList() throws PlanPalExceptions {
        viewList(getRecurringExpensesList());
    }

    public void deleteRecurringExpense(String description) throws PlanPalExceptions {
        String index = ExpenseModeFunctions.removeRecurring(description);
        if (index.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        boolean hasTwoBeforeDelete = (recurringExpensesList.size() == 2);
        deleteList(recurringExpensesList, index);
        savedExpenses.saveList(recurringExpensesList, hasTwoBeforeDelete);
    }
}
