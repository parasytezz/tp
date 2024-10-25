package seedu.planpal.expenses;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.filemanager.Storeable;

/**
 * Represents an expense in the PlanPal application.
 */
public class Expense implements Editable, Storeable {
    private static final String STORAGE_PATH = "./data/expenses.txt";
    private static final String CATEGORY_SEPARATOR = "/";
    private static final String CATEGORY_VALUE_SEPARATOR = ":";
    private String commandDescription;
    private String cost;
    private String name;

    /**
     * Constructs an Expense object from a command description.
     *
     * @param description The command description containing name and cost separated by categories.
     * @throws PlanPalExceptions If the description is invalid or incomplete.
     */
    public Expense(String description) throws PlanPalExceptions {
        setCommandDescription(description);
        String[] categories = description.split(CATEGORY_SEPARATOR);
        if (categories.length == 1) {
            throw new IllegalCommandException();
        }
        assert categories.length >= 2: "Illegal command executed in expenses";
        for (int categoryIndex = 1; categoryIndex < categories.length; categoryIndex++) {
            processEditFunction(categories[categoryIndex]);
        }
    }

    /**
     * Returns a string representation of the expense.
     *
     * @return A string in the format: [name, Cost = cost].
     */
    @Override
    public String toString() {
        return "[" + name + ", cost = $" + cost + "]";
    }

    /**
     * Processes an edit command for a given input, updating the expense's attributes.
     *
     * @param input The input containing a category and its new value.
     * @throws PlanPalExceptions If the input is incomplete or contains an invalid category.
     */
    @Override
    public void processEditFunction(String input) throws PlanPalExceptions {
        String[] inputParts = input.split(CATEGORY_VALUE_SEPARATOR);
        if (inputParts.length < 2) {
            throw new PlanPalExceptions("The command is incomplete. Please provide a value for " + inputParts[0]);
        }

        String category = inputParts[0].trim();
        String valueToEdit = inputParts[1].trim();
        if (category.equals("cost")){
            setCost(valueToEdit);
        } else if (category.equals("name")){
            setName(valueToEdit);
        }else {
            System.out.println(category + " is not a valid category");
            throw new IllegalCommandException();
        }
        setCommandDescription(category, valueToEdit);
    }

    /**
     * Updates the command description when a specific category is modified.
     *
     * @param categoryToChange The category that needs to be updated.
     * @param newValue The new value for the category.
     * @throws PlanPalExceptions If there is an error updating the command description.
     */
    // Overloaded function
    public void setCommandDescription(String categoryToChange, String newValue) throws PlanPalExceptions {
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

    @Override
    public void setCommandDescription(String description) {
        this.commandDescription = description;
    }

    @Override
    public String getCommandDescription() {
        return commandDescription;
    }

    @Override
    public String getStoragePath() {
        return STORAGE_PATH;
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
        double costValue = Double.parseDouble(cost);
        if (costValue < 0) {
            throw new PlanPalExceptions("Cost cannot be negative");
        }
        this.cost = cost;
    }

    /**
     * Gets the cost of the expense.
     *
     * @return The cost of the expense.
     */
    public String getCost(){
        return cost;
    }
}
