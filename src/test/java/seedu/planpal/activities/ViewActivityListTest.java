package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewActivityListTest {
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
    public void viewActivityList_success() {
        try {
            activityManager.addActivity("/name: swimming /type: exercise");
            activityManager.viewActivityList();
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = swimming, type = exercise]"));
            OUTPUT_STREAM.reset();
            activityParser.processCommand("list");
            assertTrue(OUTPUT_STREAM.toString().contains("[activity = swimming, type = exercise]"));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
