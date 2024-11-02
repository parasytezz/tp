package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.ExpenseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteExpenseTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ExpenseManager expenseManager;

    @BeforeEach
    public void setUp() {
        expenseManager = new ExpenseManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
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
}
