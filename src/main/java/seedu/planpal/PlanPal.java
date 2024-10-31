package seedu.planpal;


import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.parser.Parser;

import java.util.Scanner;


public class PlanPal {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Ui.printWelcomeMessage();

        while (true) {
            try {
                Ui.printAvailableModes();
                String modeInput = in.nextLine();
                Parser mainParser = new Parser();
                mainParser.processCommand(modeInput);
            } catch (PlanPalExceptions e) {
                Ui.print(e.getMessage());
            }
        }

    }
}
