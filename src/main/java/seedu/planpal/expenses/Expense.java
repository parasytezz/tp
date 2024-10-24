package seedu.planpal.expenses;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.filemanager.Storeable;

public class Expense implements Editable, Storeable {
    private static final String STORAGE_PATH = "./data/expenses.txt";
    private static final String CATEGORY_SEPARATOR = "/";
    private static final String CATEGORY_VALUE_SEPARATOR = ":";
    private String commandDescription;
    private String cost;

    public Expense(String description) throws PlanPalExceptions {
        setCommandDescription(description);
        String[] categories = description.split(CATEGORY_SEPARATOR);
        if (categories.length == 1) {
            throw new IllegalCommandException();
        }
        assert categories.length >= 2: "Illegal command executed in expenses";
        for (int catagoryIndex = 1; catagoryIndex < categories.length; catagoryIndex++) {
            processEditFunction(categories[catagoryIndex]);
        }
    }

    @Override
    public String toString() {
        return "[Cost = " + cost + "]";
    }

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
        } else {
            System.out.println(category + " is not a valid category");
            throw new IllegalCommandException();
        }
        setCommandDescription(category, valueToEdit);
    }

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

    public void setCost(String cost) {
        this.cost = cost;
    }
}
