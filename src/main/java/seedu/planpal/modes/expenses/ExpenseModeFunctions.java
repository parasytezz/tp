package seedu.planpal.modes.expenses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public interface ExpenseModeFunctions {
    String MONTH_SEPARATOR = "/month:";

    /**
     * Retrieves the current month in the "yyyy-MM" format.
     *
     * @return The current month as a string in "yyyy-MM" format.
     */
    default String getCurrentMonth(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * Extracts the month from the input string if it contains "/month:". If "/month:" is not found, returns null.
     *
     * @param input The input string potentially containing the month specification.
     * @return The extracted month in "yyyy-MM" format or null if not found.
     */
    default String getMonth(String input){
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

    /**
     * Calculates the total cost of all expenses for a specified month.
     *
     * @param month The month for which the total cost is calculated.
     * @return The total cost of expenses for the specified month.
     */
    default double getTotalCost(String month, Map<String, ArrayList<Expense>> monthlyExpenses){
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
    default double getTotalCost(Map<String, ArrayList<Expense>> monthlyExpenses){
        return getTotalCost(getCurrentMonth(), monthlyExpenses);
    }
}
