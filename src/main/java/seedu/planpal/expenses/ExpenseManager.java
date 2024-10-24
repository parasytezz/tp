package seedu.planpal.expenses;


import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
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
    }

}
