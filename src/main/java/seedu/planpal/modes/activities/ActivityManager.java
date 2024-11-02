package seedu.planpal.modes.activities;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;

public class ActivityManager implements ListFunctions {
    public static final String[] INFORMATION_CATEGORIES = {"name", "activityType"};
    FileManager savedActivities = new FileManager();
    private final ArrayList<Activity> activityList = new ArrayList<>();

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    /**
     * Adds a new activity to the activity list.
     * This method creates a new Activity instance with the specified name
     * and activity type, then adds it to the activityList. If either the
     * name or the activity type is empty, a PlanPalExceptions will be thrown.
     *
     * @param name the name of the activity to be added
     * @param activityType the type of the activity to be added
     * @throws PlanPalExceptions if the name or activity type is empty
     */
    public void addActivity(String description) throws PlanPalExceptions {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        addToList(activityList, new Activity(description));
        savedActivities.saveList(activityList);
    }

    /**
     * Deletes an existing activity from the activity list.
     * The activity is retrieved from its description.
     *
     * @param index The description of the activity to be deleted. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} os thrown.
     */
    public void deleteActivity(String index) throws PlanPalExceptions {
        if (index.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        assert index.length() != 0: "Index should not be empty";

        try {
            boolean hasTwoBeforeDelete = (activityList.size() == 2);
            deleteList(activityList, index);
            savedActivities.saveList(activityList, hasTwoBeforeDelete);
        } catch (PlanPalExceptions e) {
            throw e;
        }
    }

    /**
     * Displays the entire list of activities.
     * Each activity is printed with an index number.
     */
    public void viewActivityList() {
        viewList(activityList);
    }

    /**
     * Finds activity in the activity list based on the provided description.
     * If matching activities are found, they are displayed to the user.
     *
     * @param description The description of the contacts to find. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} is thrown.
     */
    public void findActivity(String description) throws PlanPalExceptions {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        assert description != null : "Description must not be null";
        assert !description.isEmpty() : "Description must not be empty";

        try {
            findInList(activityList, description);
        } catch (PlanPalExceptions e) {
            throw e;
        }
    }

    /**
     * Edits an existing activity in the activity list.
     * This method retrieves the activity at the specified index from the
     * activityList and applies the provided query to modify it.
     * If the index is out of bounds, a PlanPalExceptions will be thrown.
     *
     * @param index the index of the activity to be edited (1-based index)
     * @param query the query string used to modify the activity
     * @throws PlanPalExceptions if the index is invalid (out of range)
     */
    public void editActivity(String query) throws PlanPalExceptions {
        try {
            editList(activityList, query);
            savedActivities.saveList(activityList);
        } catch (PlanPalExceptions e) {
            throw e;
        }
    }
}
