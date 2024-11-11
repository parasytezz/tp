package seedu.planpal.utility.parser.modeparsers;

import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.BackUpManager;
import seedu.planpal.utility.parser.Parser;

/**
 * Parses user input and executes commands within the PlanPal application.
 */
public class ContactParser extends Parser {

    private static final int INPUT_SEGMENTS = 2;
    private ContactManager contactManager;

    public ContactParser(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    /**
     * Processes user commands by splitting the input into command and description.
     *
     * @param input User input string that contains a command followed by description.
     * @throws PlanPalExceptions If an invalid command is provided or the description is empty.
     */
    @Override
    public boolean processCommand(String input) throws PlanPalExceptions {
        try {
            String[] inputParts = input.split(" ", INPUT_SEGMENTS);
            String command = inputParts[0].toLowerCase();
            String description;

            switch (command) {
            case ADD_COMMAND:
                description = inputParts[1].trim();
                contactManager.addContact(description);
                return true;

            case DELETE_COMMAND:
                description = inputParts[1].trim();
                contactManager.deleteContact(description);
                return true;

            case SET_CATEGORY_COMMAND:
                boolean inCategory = true;
                while (inCategory) {
                    Ui.printCategoryMode();
                    inCategory = contactManager.handleCategory(Ui.getSetCategory(), System.out);
                }
                return true;

            case SEARCH_CATEGORY_COMMAND:
                description = inputParts[1].trim();
                contactManager.searchCategory(description);
                return true;

            case LIST_COMMAND:
                contactManager.viewContactList();
                return true;

            case EDIT_COMMAND:
                try {
                    String query = inputParts[1].trim();
                    contactManager.editContact(query);
                } catch (NumberFormatException e) {
                    throw new PlanPalExceptions("Invalid index format. Please provide a valid number.");
                }
                return true;

            case FIND_COMMAND:
                String query = inputParts[1].trim();
                contactManager.findContact(query);
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
