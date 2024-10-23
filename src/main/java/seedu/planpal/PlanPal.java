package seedu.planpal;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.filemanager.FileManager;
import seedu.planpal.utility.Parser;
import seedu.planpal.utility.Ui;

import java.util.Scanner;


public class PlanPal {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Start up everything required
        FileManager fileManager = new FileManager();
        Parser parser = new Parser(fileManager);
        Ui.printWelcomeMessage();
        fileManager.loadLists(parser, "data");

        while (true) {
            try {
                String input = in.nextLine();
                parser.processCommand(input);
            } catch (PlanPalExceptions e) {
                Ui.print(e.getMessage());
            }
        }

    }
}
