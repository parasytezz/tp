package seedu.planpal.utility.filemanager;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Parser;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
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
        } catch (PlanPalExceptions ignore) {
            return;
        }
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
            bw.write(commandType + " " + description + "\n");
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
