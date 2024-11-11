package seedu.planpal.modes.expenses.managers;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
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
        monthlyExpenses.putIfAbsent(newMonth, new ArrayList<>());
        if (isBeforeCurrentMonth(newMonth)) {
            return;
        }
        PrintStream out = System.out;
        Ui.setDummyStream();
        try {
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
        } catch (Exception e) {
            Ui.setMainStream(out);
            throw new PlanPalExceptions(e.getMessage());
        }
        Ui.setMainStream(out);
    }

    /**
     * Adds a new recurring expense to the list and saves it to the associated file.
     * @param description The description of the recurring expense to add.
     * @throws EmptyDescriptionException If the provided description is empty.
     */
    public void addRecurringExpense(String description) throws PlanPalExceptions {
        if (description.isEmpty() || description.contains(MONTH_SEPARATOR)){
            throw new IllegalCommandException();
        }
        RecurringExpense newExpense = new RecurringExpense(description);
        addToList(recurringExpensesList, newExpense);
        savedExpenses.saveList(recurringExpensesList);
    }

    /**
     * Displays the list of all recurring expenses.
     * @throws PlanPalExceptions if unable to display the list.
     */
    public void viewRecurringList() throws PlanPalExceptions {
        viewList(getRecurringExpensesList());
    }

    /**
     * Deletes a recurring expense identified by its description after removing the recurring tag.
     * Throws an exception if the modified description is empty.
     * Also handles specific behaviors if exactly two items were in the list before deletion.
     *
     * @param description The description including the recurring tag to identify the expense.
     * @throws PlanPalExceptions if description is invalid or empty.
     */
    public void deleteRecurringExpense(String description) throws PlanPalExceptions {
        String index = ExpenseModeFunctions.removeRecurringTag(description);
        if (index.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        boolean hasTwoBeforeDelete = (recurringExpensesList.size() == 2);
        deleteList(recurringExpensesList, index);
        savedExpenses.saveList(recurringExpensesList, hasTwoBeforeDelete);
    }

    /**
     * Edits a recurring expense in the list based on a provided description.
     * It removes the recurring tag from the description, updates the corresponding item in the list,
     * and then saves the updated list to the file.
     *
     * @param description The description of the recurring expense to edit, including the recurring tag.
     * @throws PlanPalExceptions If the removal of the recurring tag results errors during editing.
     */
    public void editRecurringExpense(String description) throws PlanPalExceptions {
        String query = ExpenseModeFunctions.removeRecurringTag(description);
        editList(recurringExpensesList, query);
        savedExpenses.saveList(recurringExpensesList);
    }

    /**
     * Searches for a recurring expense in the list based on a provided description after removing the recurring tag.
     * If the resulting query string is empty, it throws an exception to indicate invalid input.
     *
     * @param description The description including the recurring tag of the recurring expense to find.
     * @throws PlanPalExceptions If the query string is empty or if the search encounters an error.
     */
    public void findRecurringExpense(String description) throws PlanPalExceptions {
        String query = ExpenseModeFunctions.removeRecurringTag(description);
        if (query.isEmpty()){
            throw new EmptyDescriptionException();
        }

        findInList(recurringExpensesList, query);
    }
}
