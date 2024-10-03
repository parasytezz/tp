package seedu.planpal.Utility;

public class Ui implements Functions<String>{
    public void printWelcomeMessage() {
        Functions.print(
                "Hello! I'm PlanPal.",
                "How can I be of service?"
        );
    }

    public void printByeMessage() {
        Functions.print("Bye. Hope to see you again!");
    }
}
