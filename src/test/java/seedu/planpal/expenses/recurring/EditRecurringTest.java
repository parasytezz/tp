package seedu.planpal.expenses.recurring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class EditRecurringTest {
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
    public void addValidRecurringExpense_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            assertEquals(1, expenseManager.getRecurringManager().getRecurringExpensesList().size());
            expenseParser.processCommand("edit 1 /recurring /name:trial");
            assertEquals("trial", expenseManager.getRecurringManager().getRecurringExpensesList().get(0).getName());
            expenseParser.processCommand("edit 1 /name:lol /recurring");
            assertEquals("lol", expenseManager.getRecurringManager().getRecurringExpensesList().get(0).getName());
            expenseParser.processCommand("edit 1 /name:Spotify /recurring /recurring");
            assertEquals("Spotify", expenseManager.getRecurringManager().getRecurringExpensesList().get(0).getName());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editRecurringExpenseInvalidIndex_exceptionThrown() {
        assertThrows(PlanPalExceptions.class, () ->
                expenseParser.processCommand("edit 99 /recurring /name:Invalid"));
    }

    @Test
    public void editRecurringExpenseEmptyName_exceptionThrown() {
        try {
            expenseParser.processCommand("add /recurring /name:Netflix /cost:12 /type:entertainment");
            assertThrows(PlanPalExceptions.class, () ->
                    expenseParser.processCommand("edit 1 /name: /recurring"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editRecurringPriority_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Subscription /cost:15.99 /type:entertainment");
            expenseParser.processCommand("edit 1 /recurring /month:2024-11 /name:UpdatedName");
            assertEquals("UpdatedName",
                    expenseManager.getRecurringManager().getRecurringExpensesList().get(0).getName());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
