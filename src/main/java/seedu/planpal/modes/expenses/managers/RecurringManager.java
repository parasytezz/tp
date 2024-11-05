package seedu.planpal.modes.expenses.managers;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.Expense;
import seedu.planpal.modes.expenses.ExpenseModeFunctions;
import seedu.planpal.modes.expenses.RecurringExpense;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

public class RecurringManager implements ExpenseModeFunctions, ListFunctions {
    FileManager savedExpenses;
    private ArrayList<RecurringExpense> recurringExpensesList = new ArrayList<>();

    public RecurringManager(FileManager fileManager) {
        this.savedExpenses = fileManager;
    }

    public ArrayList<RecurringExpense> getRecurringExpensesList() {
        return recurringExpensesList;
    }

    public void addRecurringToMonthlyExpenses(String newMonth, Map<String, ArrayList<Expense>> monthlyExpenses)
            throws PlanPalExceptions {
        if (monthlyExpenses.get(newMonth) != null){
            return;
        }
        PrintStream out = System.out;
        Ui.setDummyStream();
        monthlyExpenses.putIfAbsent(newMonth, new ArrayList<>());
        for (RecurringExpense recurringExpense : recurringExpensesList){
            String description =
                    recurringExpense.getCommandDescription()
                            .substring(ExpenseModeFunctions.RECURRING_TAG.length())
                            .trim();
            description += " " + MONTH_SEPARATOR + newMonth;
            Expense expense = new Expense(description);
            addToList(monthlyExpenses.get(newMonth), expense);
            savedExpenses.saveList(monthlyExpenses.get(newMonth));
        }
        Ui.setMainStream(out);
    }

    public void addRecurringExpense(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }
        RecurringExpense newExpense = new RecurringExpense(description);
        addToList(recurringExpensesList, newExpense);
        savedExpenses.saveList(recurringExpensesList);
    }
}
