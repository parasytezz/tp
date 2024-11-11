package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class EditActivityTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private ActivityManager activityManager;
    private ActivityParser activityParser;

    @BeforeEach
    public void setUp() {
        activityManager = new ActivityManager();
        activityParser = new ActivityParser(activityManager);
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
            activityParser.processCommand("edit 1 /name: surfing");
            assertEquals("surfing", activityManager.getActivityList().get(0).getName());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editActivity_invalidIndex_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("edit 3 /name: gym"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editActivity_emptyIndex_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("edit "));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editActivity_emptyList_throwsException() {
        assertThrows(PlanPalExceptions.class, () -> activityManager.editActivity("1 /name: gym"));
    }

    @Test
    public void editActivity_negativeIndex_throwsException() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("edit -1 /name: gym"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editActivity_nonNumericIndex_throwsException() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("edit abc /name: gym"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
