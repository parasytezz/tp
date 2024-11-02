package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.InvalidBudgetException;
import seedu.planpal.exceptions.expenses.NegativeBudgetException;
import seedu.planpal.exceptions.expenses.NoBudgetException;
import seedu.planpal.modes.expenses.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class ExpenseManagerTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @Test
    public void addExpenseWithoutBudget_exceptionThrown() {
        assertThrows(NoBudgetException.class, () -> expenseManager.addExpense("/cost:100"));
    }

    @Test
    public void addExpenseWithBudget_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial /cost:10");
            assertEquals(10.0, expenseManager.getTotalCost());
            expenseManager.addExpense("/name:trial /cost:20");
            assertEquals(30.0, expenseManager.getTotalCost());
            expenseManager.addExpense("/name:trial /cost:30");
            assertEquals(60.0, expenseManager.getTotalCost());
            expenseManager.addExpense("/name:trial /cost:40");
            assertEquals(100.0, expenseManager.getTotalCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addBudgetInvalidFormat_exceptionThrown() {
        assertThrows(InvalidBudgetException.class, () -> expenseManager.setBudget("invalid"));
    }

    @Test
    public void addBudgetNegativeValue_exceptionThrown() {
        assertThrows(NegativeBudgetException.class, () -> expenseManager.setBudget("-100"));
    }

    @Test
    public void addExpenseEmptyDescription_exceptionThrown() {
        try {
            expenseManager.setBudget("100");
            assertThrows(EmptyDescriptionException.class, () -> expenseManager.addExpense(""));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
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
    public void viewExpenseList_withinBudget_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:200");
            expenseManager.addExpense("/name:trial2 /cost:300");
            expenseManager.viewExpenseList();
            assertEquals(500.0, expenseManager.getTotalCost());
            assertTrue(OUTPUT_STREAM.toString().contains("Remaining budget: $500.0"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void viewExpenseList_exceedBudget_showsWarning() {
        try {
            expenseManager.setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:400");
            expenseManager.addExpense("/name:trial2 /cost:200");
            expenseManager.viewExpenseList();
            assertEquals(600.0, expenseManager.getTotalCost());
            assertTrue(OUTPUT_STREAM.toString().contains("It's time to readjust your spending habits!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editExpense_validEdit_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.editExpense("1 /cost:150");
            assertEquals(150.0, expenseManager.getTotalCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editExpense_invalidIndex_exceptionThrown() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.editExpense("2 /cost:150"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findExpense_validDescription_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.addExpense("/name:trial2 /cost:200");

            expenseManager.findExpense("trial1");
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial1, Cost = $100]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findExpense_noMatch_exceptionThrown() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");

            assertThrows(PlanPalExceptions.class, () -> expenseManager.findExpense("trial3"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setBudget_zeroBudget_throwsNoBudgetException() {
        assertThrows(NoBudgetException.class, () -> {
            expenseManager.setBudget("0");
            expenseManager.addExpense("/name:trial /cost:10");
        });
    }
}
