package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.NoBudgetException;
import seedu.planpal.modes.expenses.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AddExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void withoutBudget_exceptionThrown() {
        assertThrows(NoBudgetException.class, () -> expenseManager.addExpense("/cost:100"));
    }

    @Test
    public void withBudget_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial /cost:10");
            assertEquals(10.0, expenseManager.getTotalCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyDescription_exceptionThrown() {
        try {
            expenseManager.setBudget("100");
            assertThrows(EmptyDescriptionException.class, () -> expenseManager.addExpense(""));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void negativeCost_exceptionThrown() {
        try {
            expenseManager.setBudget("1000");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name:trial /cost:-50"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void costExceedingBudget_success() {
        try {
            expenseManager.setBudget("100");
            expenseManager.addExpense("/name:trial /cost:150");
            assertEquals(150.0, expenseManager.getTotalCost());
            assertTrue(OUTPUT_STREAM.toString().contains("It's time to readjust your spending habits!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidDescription_exceptionThrown() {
        try {
            expenseManager.setBudget("500");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name trial /cost50"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleEntries_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.addExpense("/name:trial2 /cost:200");
            expenseManager.addExpense("/name:trial3 /cost:150");
            assertEquals(450.0, expenseManager.getTotalCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyCost_exceptionThrown() {
        try {
            expenseManager.setBudget("500");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name:trial /cost:"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidCostFormat_exceptionThrown() {
        try {
            expenseManager.setBudget("500");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name:trial /cost:abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
