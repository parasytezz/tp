package seedu.planpal.modes.expenses;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.Storeable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Represents an expense in the PlanPal application.
 */
public class Expense implements Editable, Storeable {
    private static final String MONTH_PATTERN = "\\d{4}-(0[1-9]|1[0-2])"; // from gpt
    private static final String CATEGORY_SEPARATOR = "/";
    private static final String CATEGORY_VALUE_SEPARATOR = ":";
    private String commandDescription;
    private String cost = "0";
    private String name;
    private String month;
    private ExpenseType type = ExpenseType.OTHER;

    /**
     * Constructs an Expense object from a command description.
     *
     * @param description The command description containing name and cost separated by categories.
     * @throws PlanPalExceptions If the description is invalid or incomplete.
     */
    public Expense(String description) throws PlanPalExceptions {
        if (!description.contains(ExpenseModeFunctions.MONTH_SEPARATOR)){
            description += (" " + ExpenseModeFunctions.MONTH_SEPARATOR + getCurrentMonth());
        }
        setCommandDescription(description);
        String[] categories = description.split(CATEGORY_SEPARATOR);
        if (categories.length <= 1) {
            throw new IllegalCommandException();
        }
        assert categories.length >= 2 : "Illegal command executed in expenses";
        setMonth(getCurrentMonth());
        for (int categoryIndex = 1; categoryIndex < categories.length; categoryIndex++) {
            processEditFunction(categories[categoryIndex]);
        }
    }

    /**
     * Gets the current month in "yyyy-MM" format.
     *
     * @return The current month as a string.
     */
    private String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * Returns a string representation of the expense.
     *
     * @return A string in the format: [Name = name, Cost = $cost, Type = type, Month = month].
     */
    @Override
    public String toString() {
        return "[Name = " + name + ", Cost = $" + cost + ", Type = " + type + "]";
    }

    /**
     * Processes an edit command for a given input, updating the expense's attributes.
     *
     * @param input The input containing a category and its new value.
     * @throws PlanPalExceptions If the input is incomplete or contains an invalid category.
     */
    @Override
    public void processEditFunction(String input) throws PlanPalExceptions {
        if (input.isEmpty()) {
            throw new IllegalCommandException();
        }
        Ui.validateTags(input);
        String[] inputParts = input.split(CATEGORY_VALUE_SEPARATOR);
        if (inputParts.length < 2) {
            throw new IllegalCommandException();
        }

        String category = inputParts[0].trim().toLowerCase();
        String valueToEdit = inputParts[1].trim();
        switch (category) {
        case "cost":
            setCost(valueToEdit);
            break;
        case "name":
            setName(valueToEdit);
            break;
        case "type":
            setType(valueToEdit);
            break;
        case "month":
            setMonth(valueToEdit);
            break;
        default:
            System.out.println(category + " is not a valid category");
            throw new IllegalCommandException();
        }
        setCommandDescription(category, valueToEdit);
    }

    /**
     * Updates the command description when a specific category is modified.
     *
     * @param categoryToChange The category that needs to be updated.
     * @param newValue         The new value for the category.
     */
    // Overloaded function
    public void setCommandDescription(String categoryToChange, String newValue) {
        String newCommandDescription = "";
        String[] categoryParts = commandDescription.split(CATEGORY_SEPARATOR);
        for (int i = 1; i < categoryParts.length; i++) {
            if (categoryParts[i].startsWith(categoryToChange)) {
                categoryParts[i] = categoryToChange + CATEGORY_VALUE_SEPARATOR + newValue;
            }
            newCommandDescription += CATEGORY_SEPARATOR + categoryParts[i] + " ";
        }
        setCommandDescription(newCommandDescription);
    }

    /**
     * Sets the command description for this expense.
     *
     * @param description The command description to set.
     */
    @Override
    public void setCommandDescription(String description) {
        this.commandDescription = description;
    }

    /**
     * Gets the command description for this expense.
     *
     * @return The command description of the expense.
     */
    @Override
    public String getCommandDescription() {
        return commandDescription;
    }

    /**
     * Gets the storage path for this expense.
     *
     * @return The storage path as a string
     */
    @Override
    public String getStoragePath() {
        return "./data/expenses/expenses_" + getMonth() + ".txt";
    }

    /**
     * Sets the name of the expense.
     *
     * @param name The name of the expense.
     * @throws PlanPalExceptions If the name is null or empty.
     */
    public void setName(String name) throws PlanPalExceptions {
        if (name == null || name.isEmpty()) {
            throw new PlanPalExceptions("Name cannot be empty");
        }
        this.name = name;
    }

    /**
     * Gets the name of the expense.
     *
     * @return The name of the expense.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the cost of the expense.
     *
     * @param cost The cost as a string.
     * @throws PlanPalExceptions If the cost is negative or invalid.
     */
    public void setCost(String cost) throws PlanPalExceptions {
        try {
            double costValue = Double.parseDouble(cost);
            if (costValue < 0) {
                throw new PlanPalExceptions("Cost cannot be negative");
            }
            this.cost = cost;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException();
        }
    }

    /**
     * Gets the cost of the expense.
     *
     * @return The cost of the expense as a string.
     */
    public String getCost() {
        return cost;
    }

    /**
     * Sets the month for the expense. Defaults to the current month if input is null or empty.
     *
     * @param month The month as a string in "yyyy-MM" format.
     */
    public void setMonth(String month) throws PlanPalExceptions {

        if (month == null || month.isEmpty()) {
            this.month = getCurrentMonth();
        } else {
            if (!Pattern.matches(MONTH_PATTERN, month)){
                throw new PlanPalExceptions("Month value should be in the following format: yyyy-MM");
            }
            this.month = month;
        }
    }

    /**
     * Gets the month for the expense.
     *
     * @return The month of the expense in "yyyy-MM" format.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the type of the expense after validating the provided type.
     *
     * @param type The type as a string and must be FOOD, TRANSPORTATION, ENTERTAINMENT, or OTHER.
     * @throws PlanPalExceptions If the type is invalid.
     */
    public void setType(String type) throws PlanPalExceptions {
        if (!ExpenseType.isValidType(type)) {
            throw new PlanPalExceptions("Invalid type. Valid Types are: FOOD, TRANSPORTATION, ENTERTAINMENT, OTHER");
        }
        this.type = ExpenseType.valueOf(type.toUpperCase());
    }

    /**
     * Retrieves the type of the expense.
     *
     * @return The type of the expense as an ExpenseType enumeration.
     */
    public ExpenseType getType() {
        return type;
    }
}
