package seedu.planpal.expenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.modes.expenses.Expense;
import seedu.planpal.modes.expenses.ExpenseType;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

class ExpenseTypeTest {

    private Expense expense;
    private ExpenseManager expenseManager;
    private ArrayList<Expense> expenses;

    @BeforeEach
    void setUp() throws PlanPalExceptions {
        expense = new Expense("/name:Test Expense/cost:100/type:FOOD");
        expenseManager = new ExpenseManager();
        expenses = new ArrayList<>();

        expenses.add(new Expense("/name:Test Expense 1/cost:50/type:FOOD"));
        expenses.add(new Expense("/name:Test Expense 2/cost:30/type:TRANSPORTATION"));
        expenses.add(new Expense("/name:Test Expense 3/cost:20/type:ENTERTAINMENT"));
        expenses.add(new Expense("/name:Test Expense 4/cost:10/type:FOOD"));
    }

    @Test
    void testSetExpenseType_validType() {
        try {
            expense.setType("FOOD");
            assertEquals(ExpenseType.FOOD, expense.getType(), "The type should be FOOD");

            expense.setType("TRANSPORTATION");
            assertEquals(ExpenseType.TRANSPORTATION, expense.getType(), "The type should be TRANSPORTATION");

            expense.setType("ENTERTAINMENT");
            assertEquals(ExpenseType.ENTERTAINMENT, expense.getType(), "The type should be ENTERTAINMENT");

            expense.setType("OTHER");
            assertEquals(ExpenseType.OTHER, expense.getType(), "The type should be OTHER");
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown for valid types");
        }
    }

    @Test
    void testSetExpenseType_invalidType() {
        assertThrows(PlanPalExceptions.class, () -> expense.setType("INVALID_TYPE"),
                "Invalid type should throw exception");
    }

    @Test
    void testExpenseTypeIsValid_validType() {
        assertTrue(ExpenseType.isValidType("FOOD"), "FOOD should be a valid type");
        assertTrue(ExpenseType.isValidType("TRANSPORTATION"), "TRANSPORTATION should be a valid type");
        assertTrue(ExpenseType.isValidType("ENTERTAINMENT"), "ENTERTAINMENT should be a valid type");
        assertTrue(ExpenseType.isValidType("OTHER"), "OTHER should be a valid type");
    }

    @Test
    void testExpenseTypeIsValid_invalidType() {
        assertFalse(ExpenseType.isValidType("INVALID_TYPE"), "INVALID_TYPE should not be a valid type");
    }

    @Test
    void testExpenseToString_withValidType() {
        try {
            expense.setType("FOOD");
            assertEquals("[Name = Test Expense, Cost = $100, Type = FOOD]", expense.toString());

            expense.setType("TRANSPORTATION");
            assertEquals("[Name = Test Expense, Cost = $100, Type = TRANSPORTATION]", expense.toString());

            expense.setType("ENTERTAINMENT");
            assertEquals("[Name = Test Expense, Cost = $100, Type = ENTERTAINMENT]", expense.toString());

            expense.setType("OTHER");
            assertEquals("[Name = Test Expense, Cost = $100, Type = OTHER]", expense.toString());
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown for valid types");
        }
    }

    @Test
    void testExpenseInitializationWithMissingType() {
        try {
            Expense missingTypeExpense = new Expense("/name:Test Missing Type Expense/cost:100");
            assertEquals(ExpenseType.OTHER, missingTypeExpense.getType(),
                    "The default type should be OTHER when no type is provided");
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown when type is missing");
        }
    }


    @Test
    void testGetExpenseTypeProportions() {
        ArrayList<String> proportions = expenseManager.getExpenseTypeProportions(expenses);

        assertEquals(3, proportions.size(), "There should be 3 different expense types");

        assertTrue(proportions.contains("FOOD: 54.55%"), "FOOD should be 50.00%");
        assertTrue(proportions.contains("TRANSPORTATION: 27.27%"), "TRANSPORTATION should be 25.00%");
        assertTrue(proportions.contains("ENTERTAINMENT: 18.18%"), "ENTERTAINMENT should be 25.00%");
    }


    @Test
    void testGetExpenseTypeProportions_noExpenses() {
        ArrayList<String> proportions = expenseManager.getExpenseTypeProportions(new ArrayList<>());

        assertTrue(proportions.isEmpty(), "Proportions should be empty when there are no expenses");
    }


    @Test
    void testGetExpenseTypeCostBreakdown() {
        ArrayList<String> breakdown = expenseManager.getExpenseTypeCostBreakdown(expenses);

        assertEquals(3, breakdown.size(), "There should be 3 different expense types");

        assertTrue(breakdown.contains("FOOD: $60.00"), "FOOD total cost should be $60.00");
        assertTrue(breakdown.contains("TRANSPORTATION: $30.00"), "TRANSPORTATION total cost should be $30.00");
        assertTrue(breakdown.contains("ENTERTAINMENT: $20.00"), "ENTERTAINMENT total cost should be $20.00");
    }


    @Test
    void testGetExpenseTypeCostBreakdown_noExpenses() {
        ArrayList<String> breakdown = expenseManager.getExpenseTypeCostBreakdown(new ArrayList<>());

        assertTrue(breakdown.isEmpty(), "Cost breakdown should be empty when there are no expenses");
    }

    @Test
    void testGetExpenseTypeCostBreakdown_singleExpenseType() {
        try {
            ArrayList<Expense> singleTypeExpenses = new ArrayList<>();
            singleTypeExpenses.add(new Expense("/name:Test Expense 1/cost:100/type:FOOD"));
            singleTypeExpenses.add(new Expense("/name:Test Expense 2/cost:150/type:FOOD"));

            ArrayList<String> breakdown = expenseManager.getExpenseTypeCostBreakdown(singleTypeExpenses);

            assertEquals(1, breakdown.size(), "There should only be 1 expense type in the breakdown");
            assertTrue(breakdown.contains("FOOD: $250.00"), "FOOD total cost should be $250.00");
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown for single type expenses");
        }
    }

    @Test
    void testGetExpenseTypeProportions_singleExpenseType() {
        try {
            ArrayList<Expense> singleTypeExpenses = new ArrayList<>();
            singleTypeExpenses.add(new Expense("/name:Test Expense 1/cost:100/type:FOOD"));
            singleTypeExpenses.add(new Expense("/name:Test Expense 2/cost:150/type:FOOD"));

            ArrayList<String> proportions = expenseManager.getExpenseTypeProportions(singleTypeExpenses);

            assertEquals(1, proportions.size(), "There should only be 1 expense type in the proportions");
            assertTrue(proportions.contains("FOOD: 100.00%"), "FOOD should be 100.00% of the expenses");
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown for single type expenses");
        }
    }

    @Test
    void testGetExpenseTypeProportions_equalDistribution() {
        try {
            ArrayList<Expense> equalExpenses = new ArrayList<>();
            equalExpenses.add(new Expense("/name:Test Expense 1/cost:50/type:FOOD"));
            equalExpenses.add(new Expense("/name:Test Expense 2/cost:50/type:TRANSPORTATION"));
            equalExpenses.add(new Expense("/name:Test Expense 3/cost:50/type:ENTERTAINMENT"));

            ArrayList<String> proportions = expenseManager.getExpenseTypeProportions(equalExpenses);

            assertTrue(proportions.contains("FOOD: 33.33%"), "FOOD should be 33.33%");
            assertTrue(proportions.contains("TRANSPORTATION: 33.33%"), "TRANSPORTATION should be 33.33%");
            assertTrue(proportions.contains("ENTERTAINMENT: 33.33%"), "ENTERTAINMENT should be 33.33%");
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown for equal distribution of expenses");
        }
    }

    @Test
    void testGetExpenseTypeProportions_unequalDistribution() {
        try {
            ArrayList<Expense> equalExpenses = new ArrayList<>();
            equalExpenses.add(new Expense("/name:Test Expense 1/cost:30/type:FOOD"));
            equalExpenses.add(new Expense("/name:Test Expense 2/cost:50/type:TRANSPORTATION"));
            equalExpenses.add(new Expense("/name:Test Expense 3/cost:10/type:ENTERTAINMENT"));

            ArrayList<String> proportions = expenseManager.getExpenseTypeProportions(equalExpenses);

            assertTrue(proportions.contains("FOOD: 33.33%"), "FOOD should be 33.33%");
            assertTrue(proportions.contains("TRANSPORTATION: 55.56%"), "TRANSPORTATION should be 55.56%");
            assertTrue(proportions.contains("ENTERTAINMENT: 11.11%"), "ENTERTAINMENT should be 11.11%");
        } catch (PlanPalExceptions e) {
            fail("Exception should not be thrown for equal distribution of expenses");
        }
    }
}
