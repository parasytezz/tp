package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.NoBudgetException;

import seedu.planpal.modes.expenses.managers.ExpenseManager;

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
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial /cost:10");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(10.0, totalCost);
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyDescription_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("100");
            assertThrows(EmptyDescriptionException.class, () -> expenseManager.addExpense(""));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void negativeCost_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name:trial /cost:-50"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void costExceedingBudget_success() {
        try {
            expenseManager.getBudgetManager().setBudget("100");
            expenseManager.addExpense("/name:trial /cost:150");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(150.0, totalCost);
            assertTrue(OUTPUT_STREAM.toString().contains("It's time to readjust your spending habits!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidDescription_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name trial /cost50"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleEntries_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.addExpense("/name:trial2 /cost:200");
            expenseManager.addExpense("/name:trial3 /cost:150");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(450.0, totalCost);
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyCost_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name:trial /cost:"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidCostFormat_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.addExpense("/name:trial /cost:abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleBudgetsAndExpenses_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000 /month:2024-10");
            expenseManager.getBudgetManager().setBudget("1500 /month:2024-11");
            expenseManager.addExpense("/name:trial1 /cost:200 /month:2024-10");
            expenseManager.addExpense("/name:trial2 /cost:300 /month:2024-10");
            expenseManager.addExpense("/name:trial3 /cost:100 /month:2024-11");
            expenseManager.addExpense("/name:trial4 /cost:400 /month:2024-11");

            // Check total for each month
            double octCost = expenseManager.getTotalCost("2024-10", expenseManager.getMonthlyExpenses());
            double novCost = expenseManager.getTotalCost("2024-11", expenseManager.getMonthlyExpenses());
            assertEquals(500.0, octCost);
            assertEquals(500.0, novCost);
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleEntriesWithoutBudget_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000 /month:2024-10");
            expenseManager.addExpense("/name:trial1 /cost:200 /month:2024-10");
            assertThrows(NoBudgetException.class, () -> expenseManager.addExpense("/cost:400 /month:2024-11"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }


}
