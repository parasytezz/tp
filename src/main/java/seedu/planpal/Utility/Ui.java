package seedu.planpal.Utility;

/**
 * Provides user interface functionalities for the PlanPal application.
 */
public class Ui implements Functions<String>{

    /**
     * Displays a welcome message to the user at the start of the application.
     */
    public void printWelcomeMessage() {
        Functions.print(
                "Hello! I'm PlanPal.",
                "How can I be of service?"
        );
    }

    /**
     * Displays a goodbye message to the user upon exiting the application.
     */
    public void printByeMessage() {
        Functions.print("Bye. Hope to see you again!");
    }
}
