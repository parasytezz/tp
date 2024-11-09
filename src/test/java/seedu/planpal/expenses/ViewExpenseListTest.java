package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewExpenseListTest {
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
    public void withinBudget_success() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:200");
            expenseManager.addExpense("/name:trial2 /cost:300");
            expenseParser.processCommand("list");
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(500.0, totalCost);
            assertTrue(OUTPUT_STREAM.toString().contains("Remaining budget: $500.0"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void exceedBudget_showsWarning() {
        try {
            expenseManager.getBudgetManager().setBudget("500");
            expenseManager.addExpense("/name:trial1 /cost:400");
            expenseManager.addExpense("/name:trial2 /cost:200");
            expenseManager.viewExpenseList();
            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(600.0, totalCost);
            assertTrue(OUTPUT_STREAM.toString().contains("It's time to readjust your spending habits!"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validBudgetAndExpenses_displayCorrectly() {
        try {
            expenseManager.getBudgetManager().setBudget("1000");
            expenseManager.addExpense("/name:trial1 /cost:200");
            expenseManager.addExpense("/name:trial2 /cost:300");
            expenseManager.viewExpenseList();

            double totalCost = expenseManager.getTotalCost(expenseManager.getMonthlyExpenses());
            assertEquals(500.0, totalCost);
            assertTrue(OUTPUT_STREAM.toString().contains("Remaining budget: $500.0"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void noExpenses_displayNoExpensesMessage() {
        try {
            expenseManager.getBudgetManager().setBudget("1000 /month:2024-02");
            expenseManager.viewExpenseList("/month:2024-02");

            assertTrue(OUTPUT_STREAM.toString().contains("No Expenses"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
