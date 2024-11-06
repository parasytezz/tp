package seedu.planpal.expenses.recurring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindRecurringTest {
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
    public void findRecurringExpenseByName_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find /recurring Spotify");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertFalse(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findRecurringExpenseByCost_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find 15 /recurring");
            String output = OUTPUT_STREAM.toString();
            assertFalse(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findRecurringExpenseByType_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Utility /cost:60.00 /type:other");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find entertainment /recurring");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
            assertFalse(output.contains("[Name = Utility, Cost = $60.00, Type = OTHER]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findMultipleRecurringExpense_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find /recurring Spotify Netflix");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findNonExistentRecurringExpense_failure() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            OUTPUT_STREAM.reset();

            assertThrows(PlanPalExceptions.class, () -> {
                expenseParser.processCommand("find /recurring Amazon");
            });
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }



    @Test
    public void findRecurringExpenseWithEmptyDescription_failure() {
        try {
            expenseParser.processCommand("find /recurring");
            fail("Exception not thrown for invalid description format");
        } catch (EmptyDescriptionException e) {
            assertTrue(e.getMessage().contains("Description cannot be empty!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findRecurringExpenseByPartialDescription_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find /recurring Net");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
            assertFalse(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findRecurringExpenseMultipleMatches_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Apple TV /cost:25.00 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Apple Music /cost:12.00 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:20.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find /recurring Apple");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Apple TV, Cost = $25.00, Type = ENTERTAINMENT]"));
            assertTrue(output.contains("[Name = Apple Music, Cost = $12.00, Type = ENTERTAINMENT]"));
            assertFalse(output.contains("[Name = Netflix, Cost = $20.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findRecurringExpenseCaseInsensitivity_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();

            expenseParser.processCommand("find /recurring spotify");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertFalse(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));

            // Test for "Spotify" with different case
            expenseParser.processCommand("find /recurring SPOTIFY");
            output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            assertFalse(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
