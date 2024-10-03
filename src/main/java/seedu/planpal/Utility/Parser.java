package seedu.planpal.Utility;

import seedu.planpal.Contacts.ContactManager;
import seedu.planpal.PlanPalExceptions.EmptyDescriptionException;
import seedu.planpal.PlanPalExceptions.IllegalCommandException;
import seedu.planpal.PlanPalExceptions.PlanPalExceptions;

/**
 * Parses user input and executes commands within the PlanPal application.
 */
public class Parser implements Functions<String>{

    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String EDIT_COMMAND = "edit";
    private static final String FIND_COMMAND = "find";
    private static final String LIST_COMMAND = "list";
    private static final String BYE_COMMAND = "bye";
    private static final int INPUT_SEGMENTS = 2;

    Ui ui = new Ui();
    ContactManager contactManager = new ContactManager();

    /**
     * Processes user commands by splitting the input into command and description.
     *
     * @param input User input string that contains a command followed by description.
     * @throws PlanPalExceptions If an invalid command is provided or the description is empty.
     */
    public void processCommand(String input) throws PlanPalExceptions {
        try {
            String[] inputParts = input.split(" ", INPUT_SEGMENTS);
            String command = inputParts[0];

            switch (command) {
            case ADD_COMMAND:
                String description = inputParts[1].trim();
                contactManager.addContact(description);
                break;

            case LIST_COMMAND:
                contactManager.viewContactList();
                break;

            case BYE_COMMAND:
                ui.printByeMessage();
                System.exit(0);
                break;

            default:
                throw new IllegalCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EmptyDescriptionException();
        }
    }
}
