package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteActivityTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ActivityManager activityManager;

    @BeforeEach
    public void setUp() {
        activityManager = new ActivityManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void deleteActivity_validInput_success() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");
            activityManager.addActivity(("/name: sleep /type: rest"));

            activityManager.deleteActivity("2");
            assertEquals("[activity = yoga, type = exercise", activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = sleep, type = rest", activityManager.getActivityList().get(1).toString());
        } catch(PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteActivity_invalidInput_exceptionThrown() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");
            activityManager.addActivity(("/name: sleep /type: rest"));

            activityManager.deleteActivity("4");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("Invalid Index!", e.getMessage());
        }
    }

    @Test
    public void deleteActivity_emptyInput_exceptionThrown() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");
            activityManager.addActivity(("/name: sleep /type: rest"));

            activityManager.deleteActivity("");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }

}
