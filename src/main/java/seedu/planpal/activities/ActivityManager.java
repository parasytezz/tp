package seedu.planpal.activities;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;

public class ActivityManager implements ListFunctions {
    FileManager savedActivities = new FileManager();
    private final ArrayList<Activity> activityList = new ArrayList<>();

    public ArrayList<Activity> getActivityList() { return activityList; }


    /**
     * @param name
     * @param activityType
     * @throws PlanPalExceptions
     */
    public void addActivity(String name, String activityType) throws PlanPalExceptions {
        if (name.isEmpty() || activityType.isEmpty()) {
            throw new PlanPalExceptions("Please provide both activity name and type, separated by a comma.");
        }
        Activity newActivity = new Activity(name, activityType);
        addToList(activityList, newActivity);
        savedActivities.saveList(activityList);
        System.out.println("Activity added. Current list size: " + activityList.size());
        System.out.println(activityList);
    }

    /**
     * Deletes an existing activity from the activity list.
     * The activity is retrieved from its description.
     *
     * @param index The description of the activity to be deleted. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} os thrown.
     */
    public void deleteActivity(int index) throws PlanPalExceptions {
        if (index < 1 || index > activityList.size()) {
            throw new PlanPalExceptions("Invalid index");
        }
        deleteList(activityList, String.valueOf(index));
        savedActivities.saveList(activityList, true);
    }

    /**
     * Displays the entire list of activities.
     * Each activity is printed with an index number for easy reference.
     */
    public void viewActivityList() {
        System.out.println("Viewing activity list. Current list size: " + activityList.size());
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

    public void editActivity(int index, String query) throws PlanPalExceptions {
        if (index < 1 || index > activityList.size()) {
            throw new PlanPalExceptions("Invalid index. Please provide a valid number.");
        }
        Activity activity = activityList.get(index - 1);
        activity.processEditFunction(query);
        savedActivities.saveList(activityList);
    }
}
