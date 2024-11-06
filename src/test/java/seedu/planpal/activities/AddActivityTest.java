package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddActivityTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ActivityManager activityManager;

    @BeforeEach
    public void setUp() {
        activityManager = new ActivityManager();
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void addActivity_validInput_success() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");

            assertEquals("[activity = yoga, type = exercise]", activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = CS2113 TP, type = academics]", activityManager.getActivityList().get(1).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addActivity_onlyNameInput_success() {
        try {
            activityManager.addActivity("/name: swimming");
            activityManager.addActivity("/name: singing");

            assertEquals("[activity = swimming, type = others", activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = singing, type = others", activityManager.getActivityList().get(1).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addActivity_emptyInput_exceptionThrown() {
        try {
            activityManager.addActivity(" ");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("TDescription cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void addActivity_missingName_exceptionThrown() {
        try {
            activityManager.addActivity("/name: /type: exercise");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void addActivity_missingType_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /type: ");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("The command is incomplete. Please provide a value for activityType", e.getMessage());
        }
    }

}
