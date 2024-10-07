package seedu.planpal.utility;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;

import java.io.*;
import java.util.Scanner;

/**
 * Manages the loading and saving of contacts within the PlanPal application.
 */
public class FileManager {
    private static final String directoryName = "data";
    private static final String fileName = "contacts.txt";
    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String EDIT_COMMAND = "edit";

    /**
     * Constructor of FileManager
     * Load commands from stored data if exists
     *
     * @param parser Parser to load the stored data
     */
    public FileManager(Parser parser) {
        System.out.println("Loading data from file...\n");
        Scanner scanner = null;
        try {
            File f = new File(directoryName + "/" + fileName);
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("No data to be loaded.\n");
            return;
        }
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            loadData(line, parser);
        }
    }

    /**
     * Helper function to load one line of command from the stored data
     *
     * @param line data stored in a line
     * @param parser Parser to load the stored data
     */
    private void loadData(String line, Parser parser) {
        try {
            parser.processCommand(line, null);
        } catch (PlanPalExceptions ignore) {}
    }

    /**
     * Save command to the stored data
     *
     * @param commandType type of command to be stored
     * @param description the description of the contacts to find. This must not be empty.
     */
    public void save(String commandType, String description) {
        FileWriter fw;
        BufferedWriter bw;
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }
        try {
            fw = new FileWriter(directoryName + "/" + fileName, true);
            bw = new BufferedWriter(fw);
            switch (commandType) {
            case ADD_COMMAND:
                bw.write("add " + description + "\n");
                break;
            case DELETE_COMMAND:
                bw.write("edit " + description + "\n");
                break;
            default:
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
