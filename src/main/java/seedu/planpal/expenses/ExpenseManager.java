package seedu.planpal.expenses;


import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;

public class ExpenseManager implements ListFunctions {

    FileManager savedExpenses = new FileManager();
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private double budget = Double.NaN;

    public void setBudget(String budget) {
        double budgetValue = Double.parseDouble(budget);
        if (budgetValue < 0) {
            throw new IllegalArgumentException("Budget cannot be negative.");
        }
        this.budget = budgetValue;
        System.out.println("Budget has been set to: $" + this.budget);
        savedExpenses.saveList(expenseList);
    }

    public ArrayList<Expense> getContactList() {
        return expenseList;
    }

    public void addExpense(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }
        if (Double.isNaN(budget)) { // Check if the budget is set
            System.out.println("Budget has not been set!");
            System.out.println("Please enter the budget using the command: budget <value>");
            return;
        }
        addToList(expenseList, new Expense(description));
        savedExpenses.saveList(expenseList);
    }

    public double getBudget() {
        return this.budget;
    }

    public void viewExpenseList(){
        System.out.println("Total budget: " + getBudget()) ;
        viewList(expenseList);
    }
}
