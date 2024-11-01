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

            switch (command) {
            case Parser.ADD_COMMAND:
                if (inputParts.length < 2) {
                    throw new EmptyDescriptionException();
                }
                String[] activityParts = inputParts[1].split(",", ADD_SEGMENTS);
                if (activityParts.length < 2) {
                    throw new PlanPalExceptions("Please provide both activity name and type, separated by a comma.");
                }

                String activityName = activityParts[0].trim();
                String activityType = activityParts[1].trim();

                activityManager.addActivity(activityName, activityType);
                return true;
                // fall through

            case Parser.DELETE_COMMAND:
                int deleteIndex = Integer.parseInt(inputParts[1].trim());
                activityManager.deleteActivity(deleteIndex);
                return true;
                // fall through

            case Parser.LIST_COMMAND:
                activityManager.viewActivityList();
                return true;
                // fall through

            case Parser.EDIT_COMMAND:
                String[] editParts = inputParts[1].split(",", 2);
                int editIndex = Integer.parseInt(editParts[0].trim());
                String editCommand = editParts[1].trim();
                activityManager.editActivity(editIndex, editCommand);
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
