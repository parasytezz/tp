package seedu.planpal.utility.parser;

import seedu.planpal.contacts.ContactManager;
import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;

/**
 * Parses user input and executes commands within the PlanPal application.
 */
public class ContactParser extends Parser {

    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String EDIT_COMMAND = "edit";
    private static final String FIND_COMMAND = "find";
    private static final String LIST_COMMAND = "list";
    private static final String EXIT_MODE_COMMAND = "exit";
    private static final int INPUT_SEGMENTS = 2;

    ContactManager contactManager;

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
            String command = inputParts[0];
            String description;

            switch (command) {
            case ADD_COMMAND:
                description = inputParts[1].trim();
                contactManager.addContact(description);
                return true;
                // fallthrough

            case DELETE_COMMAND:
                description = inputParts[1].trim();
                contactManager.deleteContact(description);
                return true;
                // fallthrough

            case LIST_COMMAND:
                contactManager.viewContactList();
                return true;
                // fallthrough

            case EDIT_COMMAND:
                try {
                    String query = inputParts[1].trim();
                    contactManager.editContact(query);
                } catch (NumberFormatException e) {
                    throw new PlanPalExceptions("Invalid index format. Please provide a valid number.");
                }
                return true;
                // fallthrough

            case FIND_COMMAND:
                String query = inputParts[1].trim();
                contactManager.findContact(query);
                return true;
                // fallthrough

            case EXIT_MODE_COMMAND:
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
