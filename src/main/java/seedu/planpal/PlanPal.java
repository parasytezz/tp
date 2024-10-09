package seedu.planpal;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.filemanager.FileManager;
import seedu.planpal.utility.Functions;
import seedu.planpal.utility.Parser;
import seedu.planpal.utility.Ui;

import java.util.Scanner;


public class PlanPal implements Functions<String> {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        Scanner in = new Scanner(System.in);
        FileManager fileManager = new FileManager(parser);
        ui.printWelcomeMessage();

        while (true) {
            try {
                String input = in.nextLine();
                parser.processCommand(input, fileManager);
            } catch (PlanPalExceptions e) {
                Functions.print(e.getMessage());
            }
        }

    }
}
