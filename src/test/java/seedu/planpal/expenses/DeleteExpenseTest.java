package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.InvalidIndexException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteExpenseTest {
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
    public void validIndex_success() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.addExpense("/name:trial2 /cost:50");
            expenseManager.deleteExpense("2");
            assertEquals(1, expenseManager.getMonthlyExpensesValues().size());
            assertEquals(100.0, expenseManager.getTotalCost(expenseManager.getMonthlyExpenses()));
            expenseParser.processCommand("delete 1");
            assertEquals(0, expenseManager.getMonthlyExpensesValues().size());
            assertEquals(0.0, expenseManager.getTotalCost(expenseManager.getMonthlyExpenses()));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidIndex_throwsException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("delete 2"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyExpenseList_throwsException() {
        assertThrows(PlanPalExceptions.class, () -> expenseManager.deleteExpense("1"));
    }

    @Test
    public void emptyDescription_throwsEmptyDescriptionException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(EmptyDescriptionException.class, () -> expenseParser.processCommand("delete"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lastItemInList_success() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            expenseManager.deleteExpense("1");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(0, expenseManager.getMonthlyExpensesValues().size());
            assertEquals(0.0, totalCost);
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void negativeIndex_throwsException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(PlanPalExceptions.class, () -> expenseParser.processCommand("delete -1"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void nonNumericIndex_throwsException() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:100");
            assertThrows(InvalidIndexException.class, () -> expenseParser.processCommand("delete abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

}
