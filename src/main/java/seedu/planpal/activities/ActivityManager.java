package seedu.planpal.activities;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;

public class ActivityManager implements ListFunctions {

    FileManager savedActivities = new FileManager();
    private ArrayList<Activity> activityList = new ArrayList<>();

    public ArrayList<Activity> getActivityList() { return activityList; }

    public void addActivity(String description) throws PlanPalExceptions {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        addToList(activityList, new Activity(description));
        savedActivities.saveList(activityList);
    }
}
