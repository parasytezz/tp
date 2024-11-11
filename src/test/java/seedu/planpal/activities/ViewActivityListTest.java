package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewActivityListTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ActivityManager activityManager;

    @BeforeEach
    public void setUp() {
        activityManager = new ActivityManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void viewActivityList_success() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: groceries /type: necessity");
            activityManager.addActivity("/name: clubbing /type: social");
            activityManager.viewActivityList();

            assertEquals("[activity = swimming, type = exercise]",
                    activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = yoga, type = exercise]",
                    activityManager.getActivityList().get(1).toString());
            assertEquals("[activity = groceries, type = necessity]",
                    activityManager.getActivityList().get(2).toString());
            assertEquals("[activity = clubbing, type = social]",
                    activityManager.getActivityList().get(3).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
