package seedu.planpal;

import seedu.planpal.PlanPalExceptions.PlanPalExceptions;
import seedu.planpal.Utility.Functions;
import seedu.planpal.Utility.Parser;
import seedu.planpal.Utility.Ui;

import java.util.Scanner;


public class PlanPal implements Functions<String> {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        Scanner in = new Scanner(System.in);
        ui.printWelcomeMessage();

        while (true) {
            try {
                String input = in.nextLine();
                parser.processCommand(input);
            } catch (PlanPalExceptions e) {
                Functions.print(e.getMessage());
            }
        }

    }
}
