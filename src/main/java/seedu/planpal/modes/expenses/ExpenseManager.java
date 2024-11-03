package seedu.planpal.modes.expenses;


import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.InvalidBudgetException;
import seedu.planpal.exceptions.expenses.NegativeBudgetException;
import seedu.planpal.exceptions.expenses.NoBudgetException;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseManager implements ListFunctions {
    private static final int BUDGET_SEGMENT = 2;
    private static final String BUDGET_SEPARATOR = "budget_";
    private static final String MONTH_SEPARATOR = "/month:";
    private static final String TXT_SEPARATOR = ".txt";
    private static final String NON_NUMERICS = "[^0-9-.]";
    FileManager savedExpenses = new FileManager();
    private Map<String, ArrayList<Expense>> monthlyExpenses = new HashMap<>();
    private Map<String, String> monthlyBudget = new HashMap<>();

    /**
     * Retrieves the current month in the "yyyy-MM" format.
     *
     * @return The current month as a string in "yyyy-MM" format.
     */
    private String getCurrentMonth(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * Prints a message advising to readjust spending habits if the total cost for the given month exceeds the budget.
     *
     * @param month The month for which the budget status is being checked.
     */
    public void printExceededBudgetMessage(String month){
        double budgetValue = Double.parseDouble(monthlyBudget.get(month));
        if (budgetValue < getTotalCost(month)){
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
        if (!monthlyBudget.containsKey(targetMonth) || monthlyBudget.get(targetMonth).equals("0")){
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
    public ArrayList<Expense> getMonthlyExpenses(String month) {
        return monthlyExpenses.getOrDefault(month, new ArrayList<>());
    }

    /**
     * Retrieves a list of expenses for the current month.
     *
     * @return An ArrayList of expenses for the current month.
     */
    public ArrayList<Expense> getMonthlyExpenses() {
        return getMonthlyExpenses(getCurrentMonth());
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
        if (!monthlyBudget.containsKey(month) || monthlyBudget.get(month).equals("0")){
            throw new NoBudgetException();
        }
        monthlyExpenses.putIfAbsent(month, new ArrayList<>());
        ArrayList<Expense> expenseList = monthlyExpenses.get(month);
        viewList(expenseList);
        double budgetValue = Double.parseDouble(monthlyBudget.get(month));
        System.out.println("For the month of " + month);
        System.out.println("    Total budget: $" + monthlyBudget.get(month));
        System.out.println("    Total cost: $" + getTotalCost(month));
        System.out.println("    Remaining budget: $" + (budgetValue - getTotalCost(month)));
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
     * Calculates the total cost of all expenses for a specified month.
     *
     * @param month The month for which the total cost is calculated.
     * @return The total cost of expenses for the specified month.
     */
    public double getTotalCost(String month){
        ArrayList<Expense> expenseList = monthlyExpenses.get(month);
        double totalCost = 0.0;
        for (Expense expense : expenseList){
            String costInString = expense.getCost();
            if (costInString == null){
                costInString = "0";
            }
            totalCost += Double.parseDouble(costInString);
        }
        return totalCost;
    }

    /**
     * Calculates the total cost of all expenses for the current month.
     *
     * @return The total cost of expenses for the current month.
     */
    public double getTotalCost(){
        return getTotalCost(getCurrentMonth());
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
     * Sets the budget for a specific month. If the budget is negative or cannot be parsed, an exception is thrown.
     *
     * @param budget    The budget value to set.
     * @param month     The month for which the budget is being set. Uses the current month if null.
     * @param isDefault A flag indicating if the budget setting should print a confirmation message.
     * @throws PlanPalExceptions If the budget value is invalid.
     */
    public void setBudget(String budget, String month, boolean isDefault) throws PlanPalExceptions{
        try {
            String targetMonth;
            if (month == null) {
                targetMonth = getCurrentMonth();
            } else {
                targetMonth = month;
            }

            double budgetValue = Double.parseDouble(budget);
            if (budgetValue < 0){
                throw new NegativeBudgetException();
            }

            monthlyBudget.put(targetMonth, budget);
            savedExpenses.saveValue("budgets/budget_" + targetMonth + ".txt", budget);
            if (isDefault) {
                Ui.print("For the month of " + targetMonth,
                        "Budget has been set to: $" + monthlyBudget.get(targetMonth));
            }

        } catch (NumberFormatException e) {
            throw new InvalidBudgetException();
        }
    }

    /**
     * Sets the budget for a specific month using a formatted input string.
     * Extracts month and budget from the input and sets them accordingly.
     *
     * @param input The input string containing the budget and month.
     * @throws PlanPalExceptions If the input is invalid.
     */
    public void setBudget(String input) throws PlanPalExceptions{
        String month = getMonth(input);
        if (month == null){
            month = getCurrentMonth();
        }
        String budget = input.replaceAll(month,"").replaceAll(NON_NUMERICS, "").trim();
        setBudget(budget, month, true);
    }

    /**
     * Sets multiple budgets from a list of strings, each representing a budget for a month.
     *
     * @param budgetList The list of budget strings in the format "fileName: budget".
     * @throws PlanPalExceptions If any budget setting encounters an error.
     */
    public void setAllBudget(ArrayList<String> budgetList) throws PlanPalExceptions{
        try {
            for (String budget : budgetList){
                String[] budgetParts = budget.split(":", BUDGET_SEGMENT);
                String fileName = budgetParts[0].trim();
                String month = fileName.replace(BUDGET_SEPARATOR,"").replace(TXT_SEPARATOR, "").trim();
                budget = budgetParts[1].trim();
                setBudget(budget, month, false);
            }
        } catch (Exception e) {
            throw new PlanPalExceptions(e.getMessage());
        }
    }

    /**
     * Retrieves the budget for a specific month.
     *
     * @param month The month for which the budget is retrieved.
     * @return The budget for the specified month as a string.
     */
    public String getBudget(String month){
        return monthlyBudget.get(month);
    }

    /**
     * Retrieves the budget for the current month.
     *
     * @return The budget for the current month as a string.
     */
    public String getBudget() {
        return getBudget(getCurrentMonth());
    }

    /**
     * Extracts the month from the input string if it contains "/month:". If "/month:" is not found, returns null.
     *
     * @param input The input string potentially containing the month specification.
     * @return The extracted month in "yyyy-MM" format or null if not found.
     */
    private String getMonth(String input){
        int startIndex = input.indexOf(MONTH_SEPARATOR);
        if (startIndex != -1){
            startIndex += MONTH_SEPARATOR.length();
            int endIndex = input.indexOf("/", startIndex);
            if (endIndex == -1){
                endIndex = input.length();
            }
            return input.substring(startIndex, endIndex).trim();
        }
        return null;
    }
}
