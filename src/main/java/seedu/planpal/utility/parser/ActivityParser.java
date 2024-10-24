package seedu.planpal.utility.parser;

import seedu.planpal.activities.ActivityManager;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;

public class ActivityParser extends Parser {
    private static final int INPUT_SEGMENTS = 2;
    ActivityManager activityManager;

    public ActivityParser(ActivityManager activityManager) { this.activityManager = activityManager; }

    @Override
    public boolean processCommand(String input) throws PlanPalExceptions {
        try {
            String[] inputParts = input.split(" ", INPUT_SEGMENTS);
            String command = inputParts[0];
            String description;

            switch (command) {
            case Parser.ADD_COMMAND:
                description = inputParts[1].trim();
                activityManager.addActivity(description);
                return true;
                // fall through

            case Parser.EXIT_MODE_COMMAND:
                break;

            case Parser.BYE_COMMAND:
                Ui.printByeMessage();
                System.exit(0);
                break;

            default:
                throw new IllegalCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EmptyDescriptionException();
        }
        return false;
    }

}
