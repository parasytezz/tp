package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.InvalidIndexException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.ExpenseManager;

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
    }

    @Test
    public void deleteExpense_validIndex_success() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.addExpense("/name:trial2 /cost:50");
            expenseManager.deleteExpense("1");
            assertEquals(1, expenseManager.getExpenseList().size());
            assertEquals(50.0, expenseManager.getTotalCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteExpense_invalidIndex_throwsException() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("2"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteExpense_emptyExpenseList_throwsException() {
        assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("1"));
    }

    @Test
    public void deleteExpense_emptyDescription_throwsEmptyDescriptionException() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(EmptyDescriptionException.class, () -> expenseManager.deleteExpense(""));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteExpense_lastItemInList_success() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.deleteExpense("1");
            assertEquals(0, expenseManager.getExpenseList().size());
            assertEquals(0.0, expenseManager.getTotalCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteExpense_negativeIndex_throwsException() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("-1"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteExpense_nonNumericIndex_throwsException() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(InvalidIndexException.class, () -> expenseManager.deleteExpense("abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

}
