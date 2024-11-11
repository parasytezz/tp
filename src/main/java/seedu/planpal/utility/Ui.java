package seedu.planpal.utility;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.contacts.Contact;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides user interface functionalities for the PlanPal application.
 */
public class Ui {
    protected static final String LINE_SEPARATOR = "_________________________________________________________";
    /**
     * Prints a series of messages enclosed between line separators.
     * This method is useful for clear and structured display of information in the console.
     *
     * @param messages an array of messages to be printed; each message is printed on a new line
     */
    public static void print(String... messages){
        System.out.println(LINE_SEPARATOR);
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Displays a welcome message to the user at the start of the application.
     */
    public static void printWelcomeMessage() {
        print(
                "Hello! I'm PlanPal.",
                "How can I be of service?"
        );
    }

    public static void printLine() {
        System.out.println(LINE_SEPARATOR);
    }

    public static void printAvailableModes(){
        print(
                "Currently, I am able to manage the following:",
                "   1. Contacts",
                "   2. Expenses",
                "   3. Activities",
                "What would you like me to manage?",
                "To select, type in the index! (Eg. To manage Contacts, type 1)"
        );
    }

    /**
     * Displays a goodbye message to the user upon exiting the application.
     */
    public static void printByeMessage() {
        print("Bye. Hope to see you again!");
    }

    public static void printCreateStorage(String pathToStorage) {
        print("Created a new storage at ", pathToStorage);
    }

    public static void printCategoryMenu(){
        print("Category options :",
                "1. add Category type [ add {category} || e.g. add close friend ]",
                "2. remove Category type [ remove {category} || e.g. remove emergency ]",
                "3a. edit Category of Contact " +
                        "[ edit {contact index} {category1/category2/...} || e.g. edit 1 friend/family ]",
                "3b. delete all Category of Contact ([ edit {contact index} /  || e.g. edit 1 / ]" +
                        " or [ edit {contact index} || e.g. edit 1 ])",
                "4. view Category lists (e.g. view)",
                "5. view Contact list (e.g. list)",
                "6. print category functions (e.g. help)",
                "7. exit"
        );
    }

    /**
     * Displays functions of category
     */
    public static String getSetCategory() {
        System.out.print("Enter Command: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Displays contacts of particular type of category
     *
     * @param category category to search
     * @param contactListByCategory 2d ArrayList of Contact where i-th element  is the contacts in category of index i
     * @param categoryList ArrayList of Categories and their corresponding position in this array is their index
     */
    public static void printCategory(String category,
                        ArrayList<ArrayList<Contact>> contactListByCategory, ArrayList<String> categoryList) {
        System.out.println("Contacts in category: " + category);
        if (contactListByCategory.get(categoryList.indexOf(category)).isEmpty()) {
            System.out.println("There is no contact in " + category);
            System.out.println(LINE_SEPARATOR);
        } else {
            for (Contact contact : contactListByCategory.get(categoryList.indexOf(category))) {
                System.out.println(contact);
            }
            System.out.println(LINE_SEPARATOR);
        }
    }

    /**
     * Displays when category is not found
     */
    public static void printCategoryNotFound() {
        System.out.println("Category not found.");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Set stream to dummy stream so that output is not displayed
     */
    public static void setDummyStream(){
        // Redirect System.out to a dummy stream (solution from gpt)
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
    }

    /**
     * Set stream to main stream so that output is displayed
     */
    public static void setMainStream(PrintStream stream){
        System.setOut(stream);
    }

    public static void clearScreen(){
        for (int i = 0; i < 50; i++){
            System.out.println(" ");
        }
    }

    public static void validateTags(String input) throws PlanPalExceptions{
        assert !input.isEmpty() : "Input should not be empty";
        int colonCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                colonCount++;
            }
        }

        if (colonCount > 1) {
            throw new PlanPalExceptions("Are you missing a '/' somewhere?");
        }
    }

    /**
     * Display when exiting setting category mode
     */
    public static void printCategoryExit() {
        print("exit category");
    }

    /**
     * Display when entering setting category mode
     */
    public static void printCategoryMode() {
        System.out.println("\nCurrent Mode: setting category mode");
    }
}
