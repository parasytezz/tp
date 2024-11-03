package seedu.planpal.modes.expenses;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.NoBudgetException;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseManager implements ListFunctions, ExpenseModeFunctions {
    FileManager savedExpenses = new FileManager();
    BudgetManager budgetManager = new BudgetManager(savedExpenses);
    private Map<String, ArrayList<Expense>> monthlyExpenses = new HashMap<>();

    /**
     * Prints a message advising to readjust spending habits if the total cost for the given month exceeds the budget.
     *
     * @param month The month for which the budget status is being checked.
     */
    public void printExceededBudgetMessage(String month){
        double budgetValue = Double.parseDouble(budgetManager.getMonthlyBudget().get(month));
        if (budgetValue < getTotalCost(month, monthlyExpenses)){
            System.out.println("It's time to readjust your spending habits!");
        }
    }

    /**
     * Adds a new expense entry for the specified month based on the provided description.
     * Throws an exception if the month lacks a budget.
     *
     * @param description The description of the expense to be added.
     * @throws PlanPalExceptions If the description is empty or no budget is set for the month.
     */
    public void addExpense(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }

        Expense newExpense = new Expense(description);
        String targetMonth = newExpense.getMonth();
        if (!budgetManager.getMonthlyBudget().containsKey(targetMonth) ||
                budgetManager.getMonthlyBudget().get(targetMonth).equals("0")){
            throw new NoBudgetException();
        }

        monthlyExpenses.putIfAbsent(targetMonth, new ArrayList<>());
        addToList(monthlyExpenses.get(targetMonth), newExpense);
        printExceededBudgetMessage(targetMonth);
        savedExpenses.saveList(monthlyExpenses.get(targetMonth));
    }

    /**
     * Retrieves a list of expenses for a specified month.
     *
     * @param month The month for which expenses are being retrieved.
     * @return An ArrayList of expenses for the specified month.
     */
    public ArrayList<Expense> getMonthlyExpensesValues(String month) {
        return monthlyExpenses.getOrDefault(month, new ArrayList<>());
    }

    /**
     * Retrieves a list of expenses for the current month.
     *
     * @return An ArrayList of expenses for the current month.
     */
    public ArrayList<Expense> getMonthlyExpensesValues() {
        return getMonthlyExpensesValues(getCurrentMonth());
    }

    /**
     * Displays the expense list and budget summary for a specified month. If no budget is set,
     * an exception is thrown.
     *
     * @param input The input string containing the month information.
     * @throws PlanPalExceptions If no budget is set for the specified month.
     */
    public void viewExpenseList(String input) throws PlanPalExceptions {
        String month = getMonth(input);
        if (!budgetManager.getMonthlyBudget().containsKey(month) ||
                budgetManager.getMonthlyBudget().get(month).equals("0")){
            throw new NoBudgetException();
        }
        monthlyExpenses.putIfAbsent(month, new ArrayList<>());
        ArrayList<Expense> expenseList = monthlyExpenses.get(month);
        viewList(expenseList);
        double budgetValue = Double.parseDouble(budgetManager.getMonthlyBudget().get(month));
        System.out.println("For the month of " + month);
        System.out.println("    Total budget: $" + budgetManager.getMonthlyBudget().get(month));
        System.out.println("    Total cost: $" + getTotalCost(month, monthlyExpenses));
        System.out.println("    Remaining budget: $" + (budgetValue - getTotalCost(month, monthlyExpenses)));
        Ui.printLine();
    }

    /**
     * Displays the expense list and budget summary for the current month.
     *
     * @throws PlanPalExceptions If no budget is set for the current month.
     */
    public void viewExpenseList() throws PlanPalExceptions {
        String monthInput = MONTH_SEPARATOR + getCurrentMonth();
        viewExpenseList(monthInput);
    }

    /**
     * Edits an existing expense entry in the specified month's list based on the provided query.
     *
     * @param query The query string specifying the expense to edit.
     * @throws PlanPalExceptions If the query is empty or fails to find a matching expense.
     */
    public void editExpense(String query) throws PlanPalExceptions {
        String month = getMonth(query);
        if (month == null){
            month = getCurrentMonth();
        }
        monthlyExpenses.putIfAbsent(month, new ArrayList<>());
        editList(monthlyExpenses.get(month), query);
        printExceededBudgetMessage(month);
        savedExpenses.saveList(monthlyExpenses.get(month));
    }

    /**
     * Deletes an expense entry from the specified month's list based on the provided input.
     *
     * @param input The input specifying the expense to delete.
     * @throws PlanPalExceptions If the input is empty or fails to delete the expense.
     */
    public void deleteExpense(String input) throws PlanPalExceptions {
        if (input.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        String month = getMonth(input);
        if (month == null){
            month = getCurrentMonth();
        }
        String index = input.replaceAll(NON_NUMERICS, "").trim();
        monthlyExpenses.putIfAbsent(month, new ArrayList<>());

        try {
            boolean hasTwoBeforeDelete = (monthlyExpenses.get(month).size() == 2);
            deleteList(monthlyExpenses.get(month), index);
            savedExpenses.saveList(monthlyExpenses.get(month), hasTwoBeforeDelete);
        } catch (PlanPalExceptions e) {
            System.out.println ("Failed to delete an expense: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Finds and displays expense entries matching the description in the specified month.
     *
     * @param description The description of the expense to find.
     * @throws PlanPalExceptions If the description is empty or fails to find a match.
     */
    public void findExpense(String description) throws PlanPalExceptions {
        String month = getMonth(description);
        if (month == null){
            month = getCurrentMonth();
        }
        monthlyExpenses.putIfAbsent(month, new ArrayList<>());
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        assert description != null : "Description must not be null";
        assert !description.isEmpty() : "Description must not be empty";

        try {
            String query = description.replace(MONTH_SEPARATOR,"")
                    .replace(month,"")
                    .replace("/","")
                    .trim();
            if (query.isEmpty()){
                viewExpenseList(MONTH_SEPARATOR + month);
                return;
            }
            findInList(monthlyExpenses.get(month), query);
        } catch (PlanPalExceptions e) {
            throw e;
        }
    }

    /**
     * Retrieves the budget manager instance associated with this expense manager.
     *
     * @return The budget manager instance managing the budgets.
     */
    public BudgetManager getBudgetManager(){
        return budgetManager;
    }

    /**
     * Retrieves the map of monthly expenses managed by this expense manager.
     *
     * @return A map where each key is the month and each value is a list of expenses for that month.
     */
    public Map<String, ArrayList<Expense>> getMonthlyExpenses() {
        return monthlyExpenses;
    }
}
