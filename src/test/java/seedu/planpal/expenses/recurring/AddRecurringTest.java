package seedu.planpal.expenses.recurring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AddRecurringTest {
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
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addRepeatedRecurringTag_success() {
        try {
            expenseParser.processCommand("add /recurring /recurring /name:Spotify /cost:10.90 /type:entertainment");
            assertEquals(1, expenseManager.getRecurringManager().getRecurringExpensesList().size());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addRecurringExpenseEmptyDescription_exceptionThrown() {
        assertThrows(PlanPalExceptions.class, () ->
                expenseParser.processCommand("add /recurring /name: /cost:20 /type:food"));
    }

    @Test
    public void addRecurringExpenseInvalidTagCombination_exceptionThrown() {
        assertThrows(IllegalCommandException.class, () ->
                expenseParser.processCommand("add /recurring /month:2024-11 /name:Netflix /cost:15"));
    }

    @Test
    public void addRecurringExpenseNoCost_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Water /type:food");
            assertEquals("0", expenseManager.getRecurringManager().getRecurringExpensesList().get(0).getCost());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addRecurringExpenseInvalidType_exceptionThrown() {
        assertThrows(PlanPalExceptions.class, () ->
                expenseParser.processCommand("add /recurring /name:Internet /cost:30 /type:invalidtype"));
    }
}
