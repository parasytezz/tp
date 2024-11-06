package seedu.planpal.expenses.recurring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.InvalidIndexException;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteRecurringTest {
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

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void deleteRecurringExpense_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("list /recurring");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));

            expenseParser.processCommand("delete 1 /recurring");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("list /recurring");
            output = OUTPUT_STREAM.toString();
            assertFalse(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteRecurringExpenseWithInvalidIndex_failure() {
        try {
            expenseParser.processCommand("delete 0 /recurring");
            fail("Exception not thrown for invalid index");
        } catch (InvalidIndexException e) {
            assertTrue(e.getMessage().contains("Invalid Index!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteRecurringExpense_noExpensesLeft() {
        try {
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("delete 1 /recurring");
            OUTPUT_STREAM.reset();

            assertTrue(expenseManager.getRecurringManager().getRecurringExpensesList().isEmpty(),
                    "Recurring expenses list should be empty after deletion.");
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteRecurringExpenseWithInvalidIndexFormat_failure() {
        try {
            expenseParser.processCommand("delete invalidIndex");
            fail("Exception not thrown for invalid index format");
        } catch (InvalidIndexException e) {
            assertTrue(e.getMessage().contains("Invalid Index!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteWithMissingIndex_failure() {
        try {
            expenseParser.processCommand("delete /recurring"); // Missing index
            fail("Exception not thrown for missing index");
        } catch (EmptyDescriptionException e) {
            assertTrue(e.getMessage().contains("Description cannot be empty!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteWithNegativeIndex_failure() {
        try {
            expenseParser.processCommand("delete -1 /recurring");
            fail("Exception not thrown for negative index");
        } catch (InvalidIndexException e) {
            assertTrue(e.getMessage().contains("Invalid Index!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteFromEmptyList_failure() {
        try {
            expenseParser.processCommand("delete 0 /recurring");
            fail("Exception not thrown for deleting from an empty list");
        } catch (InvalidIndexException e) {
            assertTrue(e.getMessage().contains("Invalid Index!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

}
