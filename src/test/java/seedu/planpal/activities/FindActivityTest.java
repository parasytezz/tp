package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FindActivityTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ActivityManager activityManager;

    @BeforeEach
    public void setUp() {
        activityManager = new ActivityManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void findActivity_validName_success() {
        try {
            activityManager.addActivity("/name: swimming /activityType: exercise");
            activityManager.addActivity("/name: running /activityType: exercise");
            activityManager.addActivity("/name: groceries /activityType: necessity");

            activityManager.findActivity("swimming");
            assertEquals("[activity = swimming, activityType = exercise]",
                    activityManager.getActivityList().get(0).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findActivity_multipleMatches_success() {
        try {
            activityManager.addActivity("/name: swimming /activityType: exercise");
            activityManager.addActivity("/name: running /activityType: exercise");
            activityManager.addActivity("/name: groceries /activityType: necessity");

            activityManager.findActivity("exercise");
            assertEquals("[activity = swimming, activityType = exercise]",
                    activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = running, activityType = exercise]",
                    activityManager.getActivityList().get(1).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findActivity_noMatches_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /activityType: exercise");
            activityManager.addActivity("/name: running /activityType: exercise");
            activityManager.addActivity("/name: groceries /activityType: necessity");

            activityManager.findActivity("leisure");
            fail();

        } catch (PlanPalExceptions e) {
            assertEquals("No matches found!", e.getMessage());
        }
    }

    @Test
    public void findActivity_emptyDescription_exceptionThrown() {
        try {
            activityManager.findActivity("");
            fail();

        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }
}
