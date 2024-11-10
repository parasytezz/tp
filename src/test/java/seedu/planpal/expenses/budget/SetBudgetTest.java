package seedu.planpal.expenses.budget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.NegativeBudgetException;
import seedu.planpal.modes.expenses.managers.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SetBudgetTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void validIntegerBudget_success() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            assertEquals("500.0", expenseManager.getBudgetManager().getBudget());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validDoubleBudget_success() {
        try {
            expenseManager.getBudgetManager().setBudget("250.75");
            assertEquals("250.75", expenseManager.getBudgetManager().getBudget());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidFormat_exceptionThrown() {
        assertThrows(PlanPalExceptions.class, () -> expenseManager.getBudgetManager().setBudget("invalid"));
    }

    @Test
    public void negativeValue_exceptionThrown() {
        assertThrows(NegativeBudgetException.class, () -> expenseManager.getBudgetManager().setBudget("-100"));
    }

    @Test
    public void emptyBudget_exceptionThrown() {
        assertThrows(PlanPalExceptions.class, () -> expenseManager.getBudgetManager().setBudget(""));
    }
}
