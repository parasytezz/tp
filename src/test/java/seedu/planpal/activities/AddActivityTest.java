package seedu.planpal.activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AddActivityTest {
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
    public void addActivity_validInput_success() {
        try {
            activityManager.addActivity("/name: yoga /type: exercise");
            activityManager.addActivity("/name: CS2113 TP /type: academics");

            assertEquals("[activity = yoga, type = exercise]",
                    activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = CS2113 TP, type = academics]",
                    activityManager.getActivityList().get(1).toString());
            activityParser.processCommand("add /name: I love CS /type: fake news");
            assertEquals("[activity = I love CS, type = fake news]",
                    activityManager.getActivityList().get(2).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addActivity_onlyNameInput_success() {
        try {
            activityManager.addActivity("/name: swimming");
            activityManager.addActivity("/name: singing");

            assertEquals("[activity = swimming, type = others]",
                    activityManager.getActivityList().get(0).toString());
            assertEquals("[activity = singing, type = others]",
                    activityManager.getActivityList().get(1).toString());
            activityParser.processCommand("add /name: I love CS");
            assertEquals("[activity = I love CS, type = others]",
                    activityManager.getActivityList().get(2).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addActivity_emptyInput_exceptionThrown() {
        try {
            assertThrows(EmptyDescriptionException.class, () -> activityParser.processCommand("add"));
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addActivity_missingName_exceptionThrown() {
        try {
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("add /name: /type: exercise"));
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addActivity_missingType_exceptionThrown() {
        try {
            assertThrows(PlanPalExceptions.class, () -> activityParser.processCommand("add /name: swimming /type:"));
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }

}
