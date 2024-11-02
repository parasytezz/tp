package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.expenses.InvalidBudgetException;
import seedu.planpal.exceptions.expenses.NegativeBudgetException;
import seedu.planpal.exceptions.expenses.NoBudgetException;
import seedu.planpal.modes.expenses.ExpenseManager;

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
            expenseManager.setBudget("500");
            assertEquals("500", expenseManager.getBudget());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validDoubleBudget_success() {
        try {
            expenseManager.setBudget("250.75");
            assertEquals("250.75", expenseManager.getBudget());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidFormat_exceptionThrown() {
        assertThrows(InvalidBudgetException.class, () -> expenseManager.setBudget("invalid"));
    }

    @Test
    public void negativeValue_exceptionThrown() {
        assertThrows(NegativeBudgetException.class, () -> expenseManager.setBudget("-100"));
    }

    @Test
    public void zeroBudget_throwsNoBudgetException() {
        assertThrows(NoBudgetException.class, () -> {
            expenseManager.setBudget("0");
            expenseManager.addExpense("/name:trial /cost:10");
        });
    }

    @Test
    public void emptyBudget_exceptionThrown() {
        assertThrows(PlanPalExceptions.class, () -> expenseManager.setBudget(""));
    }
}
