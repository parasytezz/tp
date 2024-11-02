package seedu.planpal.modes.activities;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.filemanager.Storeable;

import java.lang.reflect.Field;

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
    public Activity(String description) throws PlanPalExceptions {
        setCommandDescription(description);
        String[] categories = description.split(CATEGORY_SEPARATOR);
        if (categories.length == 1) {
            throw new IllegalCommandException();
        }
        assert categories.length >= 2: "Illegal command executed";
        for (int categoryIndex = 1; categoryIndex < categories.length; categoryIndex++) {
            processEditFunction(categories[categoryIndex]);
        }
    }

    /**
     * Returns a string representation of the activity.
     *
     * @return A string in the format: [Activity: name (activityType)]
     */
    @Override
    public String toString() {
        return "[activity = " + name + ", activity type = " + activityType + "]";
    }

    /**
     * Processes an edit command for a given input, updating the expense's attributes.
     *
     * @param input The input containing a category and its new value.
     * @throws PlanPalExceptions If the input is incomplete or contains an invalid category.
     */
    @Override
    public void processEditFunction(String input) throws PlanPalExceptions {
        assert input != null : "Input cannot be null";
        assert !input.trim().isEmpty() : "Input cannot be empty";

        String[] inputParts = input.split(CATEGORY_VALUE_SEPARATOR);
        if (inputParts.length < 2) {
            throw new PlanPalExceptions("The command is incomplete. Please provide a value for " + inputParts[0]);
        }

        assert inputParts.length >= 2 : "Input must contain category and value";

        String category = inputParts[0].trim();
        String valueToEdit = inputParts[1].trim();

        assert category != null && !category.isEmpty() : "Category cannot be null";
        assert valueToEdit != null && !valueToEdit.isEmpty() : "Value cannot be null";

        setCommandDescription(category, valueToEdit);
    }

    /**
     * Updates the command description when a specific category is modified.
     *
     * @param categoryToChange The category that needs to be updated.
     * @param newValue The new value for the category.
     * @throws PlanPalExceptions If there is an error updating the command description.
     */
    // Overloading function
    public void setCommandDescription(String category, String val) throws PlanPalExceptions {
        boolean isCategory = false;
        for (String cat : ActivityManager.INFORMATION_CATEGORIES) {
            if (category.equals(cat)) {
                isCategory = true;
                try {
                    Field field = this.getClass().getDeclaredField(category);
                    field.setAccessible(true);
                    field.set(this, val);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new PlanPalExceptions(e.getMessage());
                }
            }
        }
        if (!isCategory) {
            throw new IllegalCommandException();
        }
        commandDescription = "";
        if (name != null) {
            commandDescription += CATEGORY_SEPARATOR + "activity" + CATEGORY_VALUE_SEPARATOR + name + " ";
        }
        if (activityType != null) {
            commandDescription += CATEGORY_SEPARATOR + "activity type" + CATEGORY_VALUE_SEPARATOR + activityType + " ";
        }
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

