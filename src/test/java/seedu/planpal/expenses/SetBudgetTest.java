package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.expenses.InvalidBudgetException;
import seedu.planpal.exceptions.expenses.NegativeBudgetException;
import seedu.planpal.exceptions.expenses.NoBudgetException;
import seedu.planpal.modes.expenses.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void addBudgetInvalidFormat_exceptionThrown() {
        assertThrows(InvalidBudgetException.class, () -> expenseManager.setBudget("invalid"));
    }

    @Test
    public void addBudgetNegativeValue_exceptionThrown() {
        assertThrows(NegativeBudgetException.class, () -> expenseManager.setBudget("-100"));
    }

    @Test
    public void setBudget_zeroBudget_throwsNoBudgetException() {
        assertThrows(NoBudgetException.class, () -> {
            expenseManager.setBudget("0");
            expenseManager.addExpense("/name:trial /cost:10");
        });
    }
}
