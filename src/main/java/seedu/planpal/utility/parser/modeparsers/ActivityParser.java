package seedu.planpal.utility.parser.modeparsers;

import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.BackUpManager;
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
            String command = inputParts[0].trim().toLowerCase();
            String description;

            switch (command) {
            case ADD_COMMAND:
                description = inputParts[1].trim();
                activityManager.addActivity(description);
                return true;

            case DELETE_COMMAND:
                description = inputParts[1].trim();
                activityManager.deleteActivity(description);
                return true;

            case LIST_COMMAND:
                activityManager.viewActivityList();
                return true;

            case EDIT_COMMAND:
                try {
                    String query = inputParts[1].trim();
                    activityManager.editActivity(query);
                } catch (NumberFormatException e) {
                    throw new PlanPalExceptions("Invalid index format. Please provide a valid number.");
                }
                return true;

            case FIND_COMMAND:
                String query = inputParts[1].trim();
                activityManager.findActivity(query);
                return true;

            case EXIT_MODE_COMMAND:
                break;

            case CLEAR_COMMAND:
                Ui.printLine();
                Ui.clearScreen();
                return true;

            case BYE_COMMAND:
                Ui.printByeMessage();
                System.exit(0);
                break;

            case BACK_UP_COMMAND:
                BackUpManager.backupData();
                Ui.print("Backup Complete!");
                return true;

            case RESTORE_COMMAND:
                BackUpManager.restoreData();
                Ui.print("Restore Complete! Now re-enter into your mode!");
                return false;

            default:
                throw new IllegalCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EmptyDescriptionException();
        }
        return false;
    }

}
