package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EditActivityTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ActivityManager activityManager;

    @BeforeEach
    public void setUp() {
        activityManager = new ActivityManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void editActivity_validIndex_success() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");

            activityManager.editActivity("1 /name: gym");

            assertEquals("gym", activityManager.getActivityList().get(0).getName());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editActivity_invalidIndex_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: clubbing /type: social");


            activityManager.editActivity("5 /name: gym");

        } catch (PlanPalExceptions e) {
            assertEquals("Invalid index. There are 4 items.", e.getMessage());
        }
    }

    @Test
    public void editActivity_emptyQuery_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            activityManager.addActivity("/name: clubbing /type: social");
            activityManager.addActivity("/name: clubbing /type: social");

            activityManager.editActivity("");
            fail();

        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }
}
