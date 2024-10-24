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
            case Parser.ADD_COMMAND:
                description = inputParts[1].trim();
                contactManager.addContact(description);
                return true;
                // fallthrough

            case Parser.DELETE_COMMAND:
                description = inputParts[1].trim();
                contactManager.deleteContact(description);
                return true;
                // fallthrough
            case Parser.SET_CATEGORY_COMMAND:
                boolean inCategory = true;
                while (inCategory) {
                    inCategory = contactManager.handleCategory(Ui.getSetCategory().trim());
                }
                return true;

            case Parser.SEARCH_CATEGORY_COMMAND:
                description = inputParts[1].trim();
                contactManager.searchCategory(description);
                return true;

            case Parser.LIST_COMMAND:
                contactManager.viewContactList();
                return true;
                // fallthrough

            case Parser.EDIT_COMMAND:
                try {
                    String query = inputParts[1].trim();
                    contactManager.editContact(query);
                } catch (NumberFormatException e) {
                    throw new PlanPalExceptions("Invalid index format. Please provide a valid number.");
                }
                return true;
                // fallthrough

            case Parser.FIND_COMMAND:
                String query = inputParts[1].trim();
                contactManager.findContact(query);
                return true;
                // fallthrough

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
