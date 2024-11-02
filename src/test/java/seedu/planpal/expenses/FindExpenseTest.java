package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FindExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @Test
    public void findExpense_validDescription_success() {
        try {
            expenseManager.setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100");
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
}
