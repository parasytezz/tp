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
import static org.junit.jupiter.api.Assertions.fail;

public class AddExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
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
}
