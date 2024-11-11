package seedu.planpal.modes.expenses.managers;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.NegativeBudgetException;
import seedu.planpal.modes.expenses.ExpenseModeFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class BudgetManager implements ExpenseModeFunctions {
    private static final String MONTH_PATTERN = "\\d{4}-(0[1-9]|1[0-2])"; // from gpt
    private static final int BUDGET_SEGMENT = 2;
    private static final String BUDGET_SEPARATOR = "budget_";
    private static final String TXT_SEPARATOR = ".txt";
    FileManager savedExpenses;
    private Map<String, String> monthlyBudget = new HashMap<>();

    /**
     * Constructor for BudgetManager. Initializes the file manager used for saving and loading budget data.
     *
     * @param fileManager The FileManager instance to handle file operations.
     */
    public BudgetManager(FileManager fileManager) {
        savedExpenses = fileManager;
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
                if (!Pattern.matches(MONTH_PATTERN, month)) {
                    throw new PlanPalExceptions("Month value should be in the following format: yyyy-MM");
                }
                targetMonth = month;
            }

            double budgetValue = Double.parseDouble(budget);
            if (budgetValue < 0){
                throw new NegativeBudgetException();
            }
            budgetValue = Math.round(budgetValue * 100.0) / 100.0;
            String budgetString = Double.toString(budgetValue);

            monthlyBudget.put(targetMonth, budgetString);
            savedExpenses.saveValue("budgets/budget_" + targetMonth + ".txt", budget);
            if (isDefault) {
                Ui.print("For the month of " + targetMonth,
                        "Budget has been set to: $" + monthlyBudget.get(targetMonth));
            }

        } catch (NumberFormatException e) {
            throw new IllegalCommandException();
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
            Ui.print("An error occurred in loading the budget files!",
                    "Restart the application and check if you have edited anything wrongly!");
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
     * Retrieves the map of monthly budgets managed by this budget manager.
     *
     * @return A map where each key is a month (in "yyyy-MM" format) and each value is the budget amount for that month.
     */
    public Map<String, String> getMonthlyBudget(){
        return monthlyBudget;
    }
}
