package seedu.planpal.modes.expenses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public interface ExpenseModeFunctions {
    String MONTH_SEPARATOR = "/month:";
    String RECURRING_TAG = "/recurring";
    String NON_NUMERICS = "[^0-9-.]";

    /**
     * Retrieves the current month in the "yyyy-MM" format.
     *
     * @return The current month as a string in "yyyy-MM" format.
     */
    default String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * Extracts the month from the input string if it contains "/month:". If "/month:" is not found, returns null.
     *
     * @param input The input string potentially containing the month specification.
     * @return The extracted month in "yyyy-MM" format or null if not found.
     */
    default String getMonth(String input) {
        int startIndex = input.indexOf(MONTH_SEPARATOR);
        if (startIndex != -1) {
            startIndex += MONTH_SEPARATOR.length();
            int endIndex = input.indexOf("/", startIndex);
            if (endIndex == -1) {
                endIndex = input.length();
            }
            return input.substring(startIndex, endIndex).trim();
        }
        return null;
    }

    /**
     * Removes the recurring tag from the input string.
     * Assumes the input contains the recurring tag and will assert this before making replacements.
     *
     * @param input The string from which the recurring tag is to be removed.
     * @return The modified string with the recurring tag removed and leading/trailing whitespace trimmed.
     */
    static String removeRecurringTag(String input){
        assert input.contains(RECURRING_TAG): "This input should have recurring tag";
        return input.replaceAll(RECURRING_TAG, "").trim();
    }

    /**
     * Calculates the total cost of all expenses for a specified month.
     *
     * @param month The month for which the total cost is calculated.
     * @return The total cost of expenses for the specified month.
     */
    default double getTotalCost(String month, Map<String, ArrayList<Expense>> monthlyExpenses) {
        ArrayList<Expense> expenseList = monthlyExpenses.get(month);
        double totalCost = 0.0;
        for (Expense expense : expenseList) {
            String costInString = expense.getCost();
            if (costInString == null) {
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
    default double getTotalCost(Map<String, ArrayList<Expense>> monthlyExpenses) {
        return getTotalCost(getCurrentMonth(), monthlyExpenses);
    }

    /**
     * Calculates the proportions of different expense types within a list of expenses.
     *
     * @param expenses The list of expenses for which the type proportions are calculated.
     * @return An ArrayList of strings, where each string represents an expense type and its proportion.
     */
    default ArrayList<String> getExpenseTypeProportions(ArrayList<Expense> expenses) {
        ArrayList<ExpenseType> uniqueTypes = new ArrayList<>();
        int totalExpenses = expenses.size();

        for (Expense expense : expenses) {
            ExpenseType type = expense.getType();
            if (!uniqueTypes.contains(type)) {
                uniqueTypes.add(type);
            }
        }

        int[] typeCounts = new int[uniqueTypes.size()];
        double[] typeProportions = new double[uniqueTypes.size()];

        for (Expense expense : expenses) {
            ExpenseType type = expense.getType();
            int index = uniqueTypes.indexOf(type);
            typeCounts[index]++;
        }

        for (int i = 0; i < uniqueTypes.size(); i++) {
            typeProportions[i] = (typeCounts[i] * 100.0) / totalExpenses;
        }

        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < uniqueTypes.size(); i++) {
            result.add(uniqueTypes.get(i) + ": " + String.format("%.2f", typeProportions[i]) + "%");
        }

        return result;
    }

    /**
     * Calculates the total cost of different expense types within a list of expenses.
     *
     * @param expenses The list of expenses for which the total for each type are calculated.
     * @return An ArrayList of strings, where each string represents an expense type and its total cost.
     */
    default ArrayList<String> getExpenseTypeCostBreakdown(ArrayList<Expense> expenses) {
        ArrayList<ExpenseType> uniqueTypes = new ArrayList<>();
        ArrayList<Double> typeCosts = new ArrayList<>();

        for (Expense expense : expenses) {
            ExpenseType type = expense.getType();
            if (!uniqueTypes.contains(type)) {
                uniqueTypes.add(type);
                typeCosts.add(0.0);
            }
        }

        for (Expense expense : expenses) {
            ExpenseType type = expense.getType();
            double cost = Double.parseDouble(expense.getCost());
            int index = uniqueTypes.indexOf(type);
            typeCosts.set(index, typeCosts.get(index) + cost);
        }

        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < uniqueTypes.size(); i++) {
            result.add(uniqueTypes.get(i) + ": $" + String.format("%.2f", typeCosts.get(i)));
        }

        return result;
    }
}
