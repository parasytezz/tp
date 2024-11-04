package seedu.planpal.utility.parser.modeparsers;

import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.parser.Parser;

public class ActivityParser extends Parser {
    private static final int INPUT_SEGMENTS = 2;
    private static final int ADD_SEGMENTS = 2;
    private final ActivityManager activityManager;

    public ActivityParser(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    @Override
    public boolean processCommand(String input) throws PlanPalExceptions {
        try {
            String[] inputParts = input.split(" ", INPUT_SEGMENTS);
            String command = inputParts[0].trim();
            String description;

            switch (command) {
            case Parser.ADD_COMMAND:
                description = inputParts[1].trim();
                activityManager.addActivity(description);
                return true;
                // fall through

            case Parser.DELETE_COMMAND:
                description = inputParts[1].trim();
                activityManager.deleteActivity(description);
                return true;
                // fall through

            case Parser.LIST_COMMAND:
                activityManager.viewActivityList();
                return true;
                // fall through

            case Parser.EDIT_COMMAND:
                try {
                    String query = inputParts[1].trim();
                    activityManager.editActivity(query);
                } catch (NumberFormatException e) {
                    throw new PlanPalExceptions("Invalid index format. Please provide a valid number.");
                }
                return true;
                // fall through

            case Parser.FIND_COMMAND:
                String query = inputParts[1].trim();
                activityManager.findActivity(query);
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
