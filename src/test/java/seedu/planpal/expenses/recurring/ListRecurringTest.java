package seedu.planpal.expenses.recurring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ListRecurringTest {
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
    public void listRecurringExpense_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Spotify /cost:10.90 /type:entertainment");
            OUTPUT_STREAM.reset();
            expenseParser.processCommand("list /recurring");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("Below is the list:"));
            assertTrue(output.contains("[Name = Spotify, Cost = $10.90, Type = ENTERTAINMENT]"));
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");
            OUTPUT_STREAM.reset();
            expenseParser.processCommand("list /recurring");
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void listRecurringExpensesWithMonthTag_success() {
        try {
            expenseParser.processCommand("add /recurring /name:Netflix /cost:15.00 /type:entertainment");

            OUTPUT_STREAM.reset();
            expenseParser.processCommand("list /recurring /month:2024-11");
            String output = OUTPUT_STREAM.toString();
            assertTrue(output.contains("Below is the list:"));
            assertTrue(output.contains("[Name = Netflix, Cost = $15.00, Type = ENTERTAINMENT]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
