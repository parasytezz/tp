package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EditExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;
    private ExpenseParser expenseParser;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        expenseParser = new ExpenseParser(expenseManager);
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void validEdit_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.editExpense("1 /cost:150");
            assertEquals(150.0, expenseManager.getTotalCost(expenseManager.getMonthlyExpenses()));
            expenseParser.processCommand("edit 1 /cost:250");
            assertEquals(250.0, expenseManager.getTotalCost(expenseManager.getMonthlyExpenses()));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidIndex_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("edit 2 /cost:150"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyDescription_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(EmptyDescriptionException.class, () -> expenseParser.processCommand("edit"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void exceedBudget_showsWarning() {
        try {
            expenseManager.getBudgetManager().setBudget("200");
            expenseManager.addExpense("/name:trial1 /cost:150");
            expenseManager.editExpense("1 /cost:250");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(250.0, totalCost);
            assertTrue(OUTPUT_STREAM.toString().contains("It's time to readjust your spending habits!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidCostFormat_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("edit 1 /cost:abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void negativeCost_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseManager.editExpense("1 /cost:-50"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyCost_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("edit 1 /cost:"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
