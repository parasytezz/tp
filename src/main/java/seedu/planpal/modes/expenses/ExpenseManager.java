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

    FileManager savedExpenses = new FileManager();
    private Map<String, ArrayList<Expense>> monthlyExpenses = new HashMap<>();
    private Map<String, String> monthlyBudget = new HashMap<>();
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private String budget;

    private String getCurrentMonth(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public ArrayList<Expense> getMonthlyExpenses(String month) {
        return monthlyExpenses.getOrDefault(month, new ArrayList<>());
    }

    public void printExceededBudgetMessage(){
        double budgetValue = Double.parseDouble(getBudget());
        if (budgetValue < getTotalCost()){
            System.out.println("It's time to readjust your spending habits!");
        }
    }

    public void printExceededBudgetMessage(String month){
        double budgetValue = Double.parseDouble(monthlyBudget.get(month));
        if (budgetValue < getTotalCost()){
            System.out.println("It's time to readjust your spending habits!");
        }
    }

    public void addExpenseNew(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }

        Expense newExpense = new Expense(description);
        if (newExpense.getMonth() == null){
            newExpense.setMonth(getCurrentMonth());
        }

        String targetMonth = newExpense.getMonth();
        if (!monthlyExpenses.containsKey(targetMonth)){
            throw new NoBudgetException();
        }
        monthlyExpenses.putIfAbsent(targetMonth, new ArrayList<>());
        addToList(monthlyExpenses.get(targetMonth), newExpense);
        printExceededBudgetMessage(targetMonth);
        savedExpenses.saveList(monthlyExpenses.get(targetMonth));
    }

    public void addExpense(String description) throws PlanPalExceptions {
        if (budget == null || budget.equals("0")) {
            throw new NoBudgetException();
        }

        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }

        addToList(expenseList, new Expense(description));
        printExceededBudgetMessage();
        savedExpenses.saveList(expenseList);
    }

    public void viewExpenseList(){
        viewList(expenseList);
        double budgetValue = Double.parseDouble(getBudget());
        System.out.println("Total budget: $" + getBudget());
        System.out.println("Total cost: $" + getTotalCost());
        System.out.println("Remaining budget: $" + (budgetValue - getTotalCost()));
        printExceededBudgetMessage();
        Ui.printLine();
    }

    public double getTotalCost(){
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

    public void editExpense(String query) throws PlanPalExceptions {
        editList(expenseList, query);
        printExceededBudgetMessage();
        savedExpenses.saveList(expenseList);
    }

    /**
     * Deletes an existing expense from the contact list.
     * The expense is retrieved from its description.
     *
     * @param index The description of the expense to be deleted. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} os thrown.
     */
    public void deleteExpense(String index) throws PlanPalExceptions {
        if (index.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        assert index.length() != 0 : "Input must not be empty";

        try {
            boolean hasTwoBeforeDelete = (expenseList.size() == 2);
            deleteList(expenseList, index);
            savedExpenses.saveList(expenseList, hasTwoBeforeDelete);
        } catch (PlanPalExceptions e) {
            System.out.println ("Failed to delete an expense: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Finds expense in the expense list based on the provided description.
     * The description can contain multiple names separated by spaces.
     * If matching contacts are found, they are displayed to the user.
     *
     * @param description The description of the expense to find. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} is thrown.
     */
    public void findExpense(String description) throws PlanPalExceptions {

        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        assert description != null : "Description must not be null";
        assert !description.isEmpty() : "Description must not be empty";

        try {
            findInList(expenseList, description);
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
                Ui.print("Budget has been set to: $" + getBudget());
            }

        } catch (NumberFormatException e) {
            throw new InvalidBudgetException();
        }
    }

    // Override setBudget function to print value by default
    public void setBudget(String budget, String month) throws PlanPalExceptions{
        setBudget(budget, month, true);
    }

    public void setBudget(String budget, boolean isDefault) throws PlanPalExceptions{
        try {
            double budgetValue = Double.parseDouble(budget);
            if (budgetValue < 0){
                throw new NegativeBudgetException();
            }
            this.budget = budget;
            savedExpenses.saveValue("budgets/budget.txt", budget);
            if (isDefault) {
                Ui.print("Budget has been set to: $" + getBudget());
            }

        } catch (NumberFormatException e) {
            throw new InvalidBudgetException();
        }
    }

    // Override setBudget function to print value by default
    public void setBudget(String budget) throws PlanPalExceptions{
        setBudget(budget, true);
    }

    public String getBudget() {
        return budget;
    }
}
