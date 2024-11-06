package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.InvalidIndexException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void validIndex_success() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.addExpense("/name:trial2 /cost:50");
            expenseManager.deleteExpense("1");
            assertEquals(1, expenseManager.getMonthlyExpensesValues().size());
            assertEquals(50.0, expenseManager.getTotalCost(expenseManager.getMonthlyExpenses()));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidIndex_throwsException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("2"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyExpenseList_throwsException() {
        assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("1"));
    }

    @Test
    public void emptyDescription_throwsEmptyDescriptionException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(EmptyDescriptionException.class, () -> expenseManager.deleteExpense(""));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lastItemInList_success() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.deleteExpense("1");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(0, expenseManager.getMonthlyExpensesValues().size());
            assertEquals(0.0, totalCost);
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void negativeIndex_throwsException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("-1"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void nonNumericIndex_throwsException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(InvalidIndexException.class, () -> expenseManager.deleteExpense("abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

}
