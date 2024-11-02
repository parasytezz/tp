package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class EditExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
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
}
