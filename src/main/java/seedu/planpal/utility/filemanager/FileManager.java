package seedu.planpal.utility.filemanager;

import seedu.planpal.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Parser;
import seedu.planpal.utility.Ui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the loading and saving of data for contacts or other objects that implement
 * the {@link Storeable} interface within the PlanPal application.
 *
 */
public class FileManager {
    private static final String ADD_COMMAND = "add";
    private final String CONTACT_CLASS = "Contact";
    private final String ACTIVITY_CLASS = "Activity";
    private final String EXPENSE_CLASS = "Expense";
    private String pathToStorage;
    private String pathToContacts;
    private String pathToActivities;
    private String pathToExpenses;

    /**
     * Constructs a FileManager for managing saving and loading of all file.
     * If the provided object implements {@link Storeable}, it will use
     * the object's storage path for file operations.
     * Each object implements {@link Storeable} will have a file for storing data
     *
     * @param storagePath the directory for storage
     * @param contactsPath the path to file from storagePath for storing Contacts
     * @param activitiesPath the path to file from storagePath for storing Activities
     * @param expensesPath the path to file from storagePath for storing Expenses
     */
    public FileManager(String storagePath, String contactsPath, String activitiesPath, String expensesPath) {
        pathToStorage = storagePath;
        pathToContacts = contactsPath;
        pathToActivities = activitiesPath;
        pathToExpenses = expensesPath;
    }

    /**
     * Ensures that the directory for the storage path exists.
     * If the directory does not exist, it creates it.
     */
    private void createDirectory(){
        File directory = new File(pathToStorage);

        if (!directory.exists()){
            directory.mkdir();
        }
    }

    /**
     * Saves a list of objects to the corresponding file in storage.
     * Each object in the list must implement {@link Storeable}. The objects are saved in
     * to the file using their command description.
     *
     * @param list The list of objects to be saved. Each object must implement {@link Storeable}.
     */
    public <T> void saveList(ArrayList<T> list) {
        createDirectory();
        if (!list.isEmpty() && list.get(0) instanceof Storeable) {
            switch (list.get(0).getClass().getSimpleName()) {
                case CONTACT_CLASS:
                    try (FileWriter writer = new FileWriter(pathToStorage+ "/" + pathToContacts)) {
                        for (T item : list) {
                            String commandDescription = ((Storeable) item).getCommandDescription();
                            writer.write(ADD_COMMAND + " " + commandDescription + "\n");
                        }
                    } catch (IOException e) {
                        Ui.print("Error saving data!");
                    }
                    break;
                case ACTIVITY_CLASS:
                    break;
                case EXPENSE_CLASS:
                    break;
                default:
                    Ui.print("Does not recognize object");
                }
        } else {
            Ui.print("Empty list!");
        }
    }

    /**
     * Loads a list of objects from each corresponding file in storage.
     * The file is parsed line by line, and each command is processed using the provided {@link Parser}.
     *
     * @param parser A {@link Parser} object used to process each line of the file.
     */
    public void loadList(Parser parser) {
        File directory = new File(pathToStorage);
        if (!directory.exists()){
            return;
        }
        PrintStream out = System.out;

        // Redirect System.out to a dummy steam (solution from gpt)
        for (File file : directory.listFiles()) {
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {}
            }));
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    parser.processCommand(scanner.nextLine());
                }
            } catch (FileNotFoundException | PlanPalExceptions e){
                Ui.print(e.getMessage());
            }
            System.setOut(out);
            Ui.print("data loaded from " + file.getName());
        }

    }
}
