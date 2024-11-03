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
    private static final String NUMERICS = "[^0-9-.]";
    FileManager savedExpenses = new FileManager();
    private Map<String, ArrayList<Expense>> monthlyExpenses = new HashMap<>();
    private Map<String, String> monthlyBudget = new HashMap<>();

    private String getCurrentMonth(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public void printExceededBudgetMessage(String month){
        double budgetValue = Double.parseDouble(monthlyBudget.get(month));
        if (budgetValue < getTotalCost(month)){
            System.out.println("It's time to readjust your spending habits!");
        }
    }

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

    public ArrayList<Expense> getMonthlyExpenses(String month) {
        return monthlyExpenses.getOrDefault(month, new ArrayList<>());
    }

    public ArrayList<Expense> getMonthlyExpenses() {
        return getMonthlyExpenses(getCurrentMonth());
    }

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

    public void viewExpenseList() throws PlanPalExceptions {
        String monthInput = MONTH_SEPARATOR + getCurrentMonth();
        viewExpenseList(monthInput);
    }

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

    public double getTotalCost(){
        return getTotalCost(getCurrentMonth());
    }

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

    public void deleteExpense(String input) throws PlanPalExceptions {
        if (input.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        String month = getMonth(input);
        if (month == null){
            month = getCurrentMonth();
        }
        String index = input.replaceAll(NUMERICS, "").trim();
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

    public void setBudget(String input) throws PlanPalExceptions{
        String budget = input.replaceAll(NUMERICS, "").trim();
        String month = getMonth(input);
        setBudget(budget, month, true);
    }

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

    public String getBudget(String month){
        return monthlyBudget.get(month);
    }

    public String getBudget() {
        return getBudget(getCurrentMonth());
    }

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
