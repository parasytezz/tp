package seedu.planpal.modes.activities;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.filemanager.Storeable;

/**
 * Represents an activity in the PlanPal application.
 */
public class Activity implements Editable, Storeable {
    private static final String STORAGE_PATH = "./data/activities.txt";
    private static final String CATEGORY_SEPARATOR = "/";
    private static final String CATEGORY_VALUE_SEPARATOR = ":";
    private String commandDescription;
    private String name;
    private String activityType;

    /**
     * Constructs an Activity object from a command description.
     *
     * @param description The command description containing name and activityType separated by categories.
     * @throws PlanPalExceptions If the description is invalid or incomplete.
     */
    public Activity(String name, String activityType) throws PlanPalExceptions {
        setName(name);
        setActivityType(activityType);
    }

    /**
     * Returns a string representation of the activity.
     *
     * @return A string in the format: [Activity: name (activityType)]
     */
    @Override
    public String toString() {
        return "[Activity: " + name + " (" + activityType + ")]";
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
        switch (category) {
        case "name":
            setName(valueToEdit);
            break;
        case "type":
            setActivityType(valueToEdit);
            break;
        default:
            throw new PlanPalExceptions("Invalid category: " + category);
        }
    }

    /**
     * Updates the command description when a specific category is modified.
     *
     * @param categoryToChange The category that needs to be updated.
     * @param newValue The new value for the category.
     * @throws PlanPalExceptions If there is an error updating the command description.
     */
    // Overloading function
    public void setCommandDescription(String categoryToChange, String newValue) throws PlanPalExceptions {
        String newCommandDescription = "";
        String[] categoryParts = commandDescription.split(CATEGORY_SEPARATOR);
        for (int i = 0; i < categoryParts.length; i++) {
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
     * Sets the name of the activity.
     *
     * @param name The name of the activity.
     * @throws PlanPalExceptions If the name is null or empty.
     */
    public void setName(String name) throws PlanPalExceptions {
        if (name == null || name.isEmpty()) {
            throw new PlanPalExceptions("Name cannot be blank.");
        }
        this.name = name;
    }

    /**
     * Sets the name of the activity.
     *
     * @param activityType The type of the activity.
     * @throws PlanPalExceptions If the activityType is null or empty.
     */
    public void setActivityType(String activityType) throws PlanPalExceptions {
        if (activityType == null || activityType.isEmpty()) {
            throw new PlanPalExceptions("Activity type cannot be blank.");
        }
        this.activityType = activityType;
    }

    /**
     * Gets the name of the activity
     *
     * @return name The name of the activity
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the activity
     *
     * @return activityType The type of the activity
     */
    public String getActivityType() {
        return activityType;
    }
}

