package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.InvalidIndexException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteActivityTest {
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
    public void deleteActivity_validInput_success() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");
            activityManager.addActivity(("/name: sleep /type: rest"));
            activityManager.deleteActivity("2");
            assertEquals(2, activityManager.getActivityList().size());
            assertEquals("[activity = yoga, type = exercise]", activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = sleep, type = rest]", activityManager.getActivityList().get(1).toString());
            activityParser.processCommand("delete 2");
            assertEquals(1, activityManager.getActivityList().size());
            assertEquals("[activity = yoga, type = exercise]", activityManager.getActivityList().get(0).toString());
        } catch(PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteActivity_invalidIndex_exceptionThrown() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");
            activityManager.addActivity(("/name: sleep /type: rest"));
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("delete 4"));

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteActivity_emptyIndex_exceptionThrown() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");
            activityManager.addActivity(("/name: sleep /type: rest"));
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("delete "));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteActivity_emptyList_throwsException() {
        assertThrows(PlanPalExceptions.class, () -> activityManager.deleteActivity("1"));
    }

    @Test
    public void deleteActivity_lastItemInList_success() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.deleteActivity("1");
            assertEquals(0, activityManager.getActivityList().size());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteActivity_negativeIndex_throwsException() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("delete -1"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteActivity_nonNumericIndex_throwsException() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            assertThrows(InvalidIndexException.class, () -> activityParser.processCommand("delete abc"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
