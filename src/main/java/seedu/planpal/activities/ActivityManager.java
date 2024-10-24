package seedu.planpal.activities;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
// import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

public class ActivityManager implements ListFunctions {

    FileManager savedActivities = new FileManager();
    private ArrayList<Activity> activityList = new ArrayList<>();
    private static final Logger ACTIVITY_LOGGER = Logger.getLogger(ActivityManager.class.getName());

    public ArrayList<Activity> getActivityList() { return activityList; }

    public ActivityManager() {
        ACTIVITY_LOGGER.setLevel(Level.SEVERE);
    }

    /**
     * Adds a new activity to the activity list.
     * The activity is created from the provided description.
     *
     * @param description The description of the new activity. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} is thrown.
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
        assert index.length() != 0 : "Input must not be empty";

        ACTIVITY_LOGGER.info("Deleting contact with the index: " + index);
        try {
            deleteList(activityList, index);
            savedActivities.saveList(activityList, true);
            ACTIVITY_LOGGER.info("Deleted contact");
        } catch (PlanPalExceptions e) {
            ACTIVITY_LOGGER.severe("Failed to delete a contact: " + e.getMessage());
            throw e;
        }
    }
}
