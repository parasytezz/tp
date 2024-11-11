package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FindActivityTest {
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
    public void findActivity_validName_success() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            activityManager.addActivity("/name: groceries /type: necessity");
            activityManager.findActivity("swimming");
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = swimming, type = exercise]"));
            OUTPUT_STREAM.reset();
            activityParser.processCommand("find swimming");
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = swimming, type = exercise]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findActivity_multipleMatches_success() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            activityManager.addActivity("/name: groceries /type: necessity");

            activityManager.findActivity("exercise");
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = swimming, type = exercise]"));
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = running, type = exercise]"));

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findActivity_noMatches_exceptionThrown() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.addActivity("/name: running /type: exercise");
            activityManager.addActivity("/name: groceries /type: necessity");
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("find leisure"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findActivity_emptyDescription_exceptionThrown() {
        assertThrows(EmptyDescriptionException.class, () -> activityParser.processCommand("find"));

    }

    @Test
    public void findActivity_multipleKeywords_success() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityParser.processCommand("add /name:running /type:exercise");
            activityManager.findActivity("swimming running");
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = swimming, type = exercise]"));
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = running, type = exercise]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
