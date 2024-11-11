package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FindExpenseTest {
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
    public void validDescription_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100 /type: other");
            expenseManager.findExpense("trial1");
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial1, Cost = $100, Type = OTHER]"));
            OUTPUT_STREAM.reset();
            expenseParser.processCommand("find trial1");
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial1, Cost = $100, Type = OTHER]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void noMatch_exceptionThrown() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100 /type: other");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("find trial3"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyDescription_exceptionThrown() {
        assertThrows(EmptyDescriptionException.class, () -> expenseParser.processCommand("find"));
    }

    @Test
    public void multipleMatches_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100 /type: other");
            expenseManager.addExpense("/name:trial1 duplicate /cost:150 /type: other");
            expenseManager.findExpense("trial1");
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial1, Cost = $100, Type = OTHER]"));
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial1 duplicate, Cost = $150, Type = OTHER]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void caseInsensitive_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:TrialCase /cost:120 /type: other");
            expenseManager.findExpense("trialcase");
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = TrialCase, Cost = $120, Type = OTHER]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleKeywords_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100 /type: other");
            expenseParser.processCommand("add /name:trial2 /cost:200 /type:other");
            expenseManager.findExpense("trial1 trial2");
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial1, Cost = $100, Type = OTHER]"));
            assertTrue(OUTPUT_STREAM.toString().contains("[Name = trial2, Cost = $200, Type = OTHER]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void ignoreBrackets_noMatches() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:100 /type: other");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("find trial3"));

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
