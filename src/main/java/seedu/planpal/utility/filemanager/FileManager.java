package seedu.planpal.utility.filemanager;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.contacts.Contact;
import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.modes.expenses.ExpenseModeFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.parser.Parser;
import seedu.planpal.utility.parser.ParserFactory;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

//@@author anlamm
/**
 * Manages the loading and saving of data for contacts or other objects that implement
 * the {@link Storeable} interface within the PlanPal application.
 *
 */
public class FileManager {
    private static final String ADD_COMMAND = "add";
    private static final String EDIT_COMMAND = "edit";
    private static final String DATA_DIRECTORY = "./data/";
    private static final String VALUE_DIRECTORY = "values/";

    /**
     * Ensures that the directory for the storage path exists.
     * If the directory does not exist, it creates it.
     */
    private void createDirectory(String storagePath){
        File file = new File(storagePath);
        File directory = file.getParentFile();
        if (!directory.exists()){
            directory.mkdirs();
        }
    }

    /**
     * Saves a list of objects to the corresponding file in storage.
     * Each object in the list must implement {@link Storeable}. The objects are saved
     * to the file using their command description. If there are two items before deletion,
     * the entire list is saved; otherwise, the list is cleared.
     *
     * @param list               The list of objects to be saved. Each object must implement {@link Storeable}.
     * @param hasTwoBeforeDelete A flag indicating if there were two items before deletion,
     *                           which affects whether the list is saved or cleared.
     * @param <T>                The type of objects in the list, implementing {@link Storeable}.
     */
    public <T> void saveList(ArrayList<T> list, boolean hasTwoBeforeDelete) {
        T listElement = list.get(0);
        if (listElement instanceof Storeable) {
            String storagePath = ((Storeable)listElement).getStoragePath();
            createDirectory(storagePath);
            try(FileWriter writer = new FileWriter(storagePath)){
                if (list.size() > 1 || hasTwoBeforeDelete) {
                    System.out.println("Currently in list:");
                    int i = 0;
                    for (T item : list) {
                        i++;
                        System.out.println(i + ". " + item.toString());
                        String commandDescription = ((Storeable) item).getCommandDescription();
                        writer.write(ADD_COMMAND + " " + commandDescription + "\n");
                    }
                    Ui.printLine();
                } else {
                    list.remove(0);
                    writer.write("");
                }
            } catch (IOException e) {
                Ui.print("Error saving data!");
            }
        }
    }

    // Overloaded
    public <T> void saveList(ArrayList<T> list) {
        saveList(list, true);
    }

    //@@author c2linaung
    /**
     * Loads and processes data from a specified file using the given manager.
     * This method reads the file line by line, utilizing a parser appropriate for the file's content,
     * which is determined by the file's name. System output during processing is suppressed to prevent
     * clutter during command execution.
     *
     * @param <T>      The type of the manager used to handle commands derived from the file's data.
     * @param manager  The manager instance for processing the commands.
     * @param folderName The folder containing the file.
     * @param fileName The name of the file to load and process.
     */
    public <T> void loadList(T manager, String folderName, String fileName) {
        PrintStream out = System.out;
        Ui.setDummyStream();

        File file = new File(DATA_DIRECTORY + folderName + "/" + fileName);
        File backupFile = new File(DATA_DIRECTORY + folderName + "/" + fileName + "_backup");

        try {
            Files.copy(file.toPath(), backupFile.toPath());
        } catch (IOException e) {
            Ui.print("Error creating backup file!");
        }

        int lineNumber = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                lineNumber++;
                Parser parser = ParserFactory.getParser(file.getName(), manager);
                String nextLine = scanner.nextLine();
                if (parser instanceof ExpenseParser){
                    String fileIdentifier = file.getName()
                            .replace(".txt","")
                            .replace("expenses_","");
                    if (!nextLine.contains(ExpenseModeFunctions.MONTH_SEPARATOR + fileIdentifier)
                            && !fileIdentifier.equals("recurring")){
                        throw new PlanPalExceptions("ERROR LOADING EXPENSE FILE!");
                    }
                }
                parser.processCommand(nextLine);
            }
        } catch (PlanPalExceptions e) {
            Ui.setMainStream(out);
            Ui.print(
                    "ERROR DETECTED: FILE IS CORRUPTED!!!",
                    "ERROR OCCURRED IN LINE " + lineNumber + " of " + file.getName() + " file",
                    "Restart the application and check the data file to prevent errors!"
            );

            try {
                Files.copy(backupFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Ui.print("Error!");
            }
        } catch (FileNotFoundException e) {
            Ui.print("FILE NOT FOUND!");
        }
        backupFile.delete();
        Ui.setMainStream(out);
    }

    /**
     * Loads and processes all files within a specified folder using the given manager.
     * Each file must end with ".txt" and is processed by the appropriate parser based
     * on its name. This function is intended for loading multiple lists from one folder.
     *
     * @param <T>       The type of the manager used to handle commands derived from the file's data.
     * @param manager   The manager instance for processing the commands.
     * @param folderName The name of the folder containing the files to load.
     */
    public <T> void loadAllLists(T manager, String folderName) {
        File directory = new File(DATA_DIRECTORY + folderName + "/");
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt") && !file.getName().equals("categories.txt")) {
                loadList(manager, folderName, file.getName());
            }
        }

        for (File file : files) {
            if (file.getName().equals("categories.txt") && folderName.equals("contacts")) {
                loadContactCategories((ContactManager) manager, folderName, file.getName());
            }
        }
    }

    /**
     * Saves a single string value to a specified file within the value directory.
     * The function will ensure the directory structure exists, and if not, it will create it.
     *
     * @param fileName The name of the file where the value should be saved.
     * @param value    The string value to save to the file.
     */
    public void saveValue(String fileName, String value){
        String storagePath = DATA_DIRECTORY + VALUE_DIRECTORY + fileName;
        createDirectory(storagePath);
        try (FileWriter writer = new FileWriter(storagePath)){
            writer.write(value);
        } catch (IOException e) {
            Ui.print("Error saving data!");
        }
    }

    /**
     * Loads a single value from a specified file within a folder. If the file does not exist,
     * it is created, and a default value is written to it. The function returns the value loaded
     * from the file or the default value if the file was newly created.
     *
     * @param folderName The name of the folder containing the file.
     * @param fileName   The name of the file from which to load the value.
     * @param value      The default value to return and save if the file does not exist.
     * @return The loaded value from the file or the default value if the file was created.
     */
    public String loadValue(String folderName, String fileName, String value){
        String storagePath = DATA_DIRECTORY + VALUE_DIRECTORY + folderName + "/" + fileName;
        PrintStream out = System.out;
        Ui.setDummyStream();

        File file = new File(storagePath);

        try {
            if (!file.exists()){
                createDirectory(storagePath);
                try (FileWriter writer = new FileWriter(file)){
                    writer.write(value);
                }
                Ui.setMainStream(out);
                return value;
            }

            try (Scanner scanner = new Scanner(file)){
                if (scanner.hasNextLine()){
                    value = scanner.nextLine().trim();
                }
            }
        } catch (IOException e) {
            Ui.print("Error loading data!");
        }

        Ui.setMainStream(out);
        return value;
    }

    /**
     * An overloaded method of loadValue, which loads a value from a specified file with a default value of "0".
     * If the file does not exist, it is created with "0" as its content, and this value is returned.
     *
     * @param fileName   The name of the file from which to load the value.
     * @param folderName The name of the folder containing the file.
     * @return The loaded value from the file or "0" if the file was created.
     */
    public String loadValue(String fileName, String folderName){
        return loadValue(fileName, folderName, "0");
    }

    /**
     * Loads all values from a specified folder within the value directory. Each file must end with ".txt".
     * The function returns an ArrayList of strings in the format "fileName : value" for each file, where
     * "fileName" is the name of the file and "value" is the content of the file.
     *
     * @param folderName The name of the folder containing the value files.
     * @return An ArrayList of strings, each formatted as "fileName : value" representing each file's content.
     */
    public ArrayList<String> loadAllValues(String folderName){
        ArrayList<String> valueList = new ArrayList<>();
        File directory = new File(DATA_DIRECTORY + VALUE_DIRECTORY + folderName + "/");
        if (!directory.exists() || !directory.isDirectory()) {
            return valueList;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return valueList;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                valueList.add(file.getName() + " : " + loadValue(folderName, file.getName()));
            }
        }
        return valueList;
    }

    //@@author anlamm
    /**
     * Saves categories of contacts to the corresponding file in storage.
     *
     * @param contactList ArrayList of contacts
     * @param contactListByCategory 2d ArrayList of Contact where i-th element is the contacts in category of index i
     * @param categoryList ArrayList of all categories
     */
    public void saveCategories(ArrayList<Contact> contactList,
                               ArrayList<ArrayList<Contact>> contactListByCategory, ArrayList<String> categoryList) {
        String storagePath = Contact.getCategoriesPath();
        createDirectory(storagePath);
        try(FileWriter writer = new FileWriter(storagePath)) {
            for (String category : categoryList) {
                writer.write(ADD_COMMAND + " " + category + "\n");
            }
            for (ArrayList<Contact> contacts : contactListByCategory) {
                for (Contact contact : contacts) {
                    writer.write(EDIT_COMMAND + " " + (contactList.indexOf(contact) + 1)  + " "
                            + categoryList.get(contactListByCategory.indexOf(contacts)) + "\n");
                }
            }
        } catch (IOException e) {
            Ui.print("Error saving data!");
        }
    }

    /**
     * Loads and processes data from categories file using the given manager.
     * This method reads the file line by line, utilizing a parser appropriate for the file's content,
     * which is determined by the file's name. System output during processing is suppressed to prevent
     * clutter during command execution.
     *
     * @param manager  The contact manager instance for processing the commands.
     * @param folderName The folder containing the file.
     * @param fileName The name of the file to load and process.
     */
    private void loadContactCategories(ContactManager manager, String folderName, String fileName) {
        PrintStream out = System.out;
        Ui.setDummyStream();

        File file = new File(DATA_DIRECTORY + folderName + "/" + fileName);
        File backupFile = new File(DATA_DIRECTORY + folderName + "/" + fileName + "_backup");

        try {
            Files.copy(file.toPath(), backupFile.toPath());
        } catch (IOException e) {
            Ui.print("Error creating backup file!");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                manager.handleCategory(scanner.nextLine(), out);
            }
        } catch (FileNotFoundException e) {
            Ui.print("FILE NOT FOUND!");
        }
        backupFile.delete();
        Ui.setMainStream(out);
    }
}
