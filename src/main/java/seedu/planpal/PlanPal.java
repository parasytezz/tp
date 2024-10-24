package seedu.planpal;

import seedu.planpal.contacts.Contact;
import seedu.planpal.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.filemanager.FileManager;
import seedu.planpal.utility.parser.ContactParser;
import seedu.planpal.utility.Ui;

import java.util.Scanner;


public class PlanPal {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Ui.printWelcomeMessage();

        // Start up everything required
        FileManager fileManager = new FileManager();
        ContactManager contactManager = new ContactManager();
        ContactParser contactParser = new ContactParser(contactManager);

        fileManager.loadList(contactManager, "contacts.txt");

        while (true) {
            try {
                String input = in.nextLine();
                contactParser.processCommand(input);
            } catch (PlanPalExceptions e) {
                Ui.print(e.getMessage());
            }
        }

    }
}
