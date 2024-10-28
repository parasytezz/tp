package seedu.planpal.expenses;


import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;

public class ExpenseManager implements ListFunctions {

    FileManager savedExpenses = new FileManager();
    private ArrayList<Expense> expenseList = new ArrayList<>();

    public ArrayList<Expense> getContactList() {
        return expenseList;
    }

    public void addExpense(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }

        addToList(expenseList, new Expense(description));
        savedExpenses.saveList(expenseList);
    }

    public void viewExpenseList(){
        viewList(expenseList);
        System.out.println("Total cost of all expenses is: $" + getTotalCost());
        Ui.printLine();
    }

    public double getTotalCost(){
        double totalCost = 0.0;
        for (Expense expense : expenseList){
            totalCost += Double.parseDouble(expense.getCost());
        }
        return totalCost;
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
            deleteList(expenseList, index);
            savedExpenses.saveList(expenseList, true);
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
}
