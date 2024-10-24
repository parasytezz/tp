package seedu.planpal.utility.filemanager;

import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.parser.Parser;
import seedu.planpal.utility.parser.ParserFactory;


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
    private static final String LIST_DIRECTORY = "./data/";

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
     * Each object in the list must implement {@link Storeable}. The objects are saved in
     * to the file using their command description.
     *
     * @param list The list of objects to be saved. Each object must implement {@link Storeable}.
     */
    public <T> void saveList(ArrayList<T> list, boolean isAfterDelete) {
        T listElement = list.get(0);
        if (listElement instanceof Storeable) {
            String storagePath = ((Storeable)listElement).getStoragePath();
            createDirectory(storagePath);
            try(FileWriter writer = new FileWriter(storagePath)){
                if (list.size() > 1 || !isAfterDelete) {
                    for (T item : list) {
                        String commandDescription = ((Storeable) item).getCommandDescription();
                        writer.write(ADD_COMMAND + " " + commandDescription + "\n");
                    }
                } else {
                    writer.write("");
                }
            } catch (IOException e) {
                Ui.print("Error saving data!");
            }
        }
    }

    // Overloaded
    public <T> void saveList(ArrayList<T> list) {
        saveList(list, false);
    }

    /**
     * Loads and processes data from a specified file using the given manager.
     * This method reads the file line by line, utilizing a parser appropriate for the file's content,
     * which is determined by the file's name. System output during processing is suppressed to prevent
     * clutter during command execution.
     *
     * @param <T> The type of the manager used to handle commands derived from the file's data.
     * @param manager The manager instance for processing the commands.
     * @param fileName The name of the file.
     */
    public <T> void loadList(T manager, String fileName) {
        File file = new File(LIST_DIRECTORY + fileName);

        PrintStream out = System.out;

        // Redirect System.out to a dummy steam (solution from gpt)
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {}
        }));

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                Parser parser = ParserFactory.getParser(file.getName(), manager);
                parser.processCommand(scanner.nextLine());
            }
        } catch (FileNotFoundException | PlanPalExceptions e){
            Ui.print(e.getMessage());
        }
        System.setOut(out);
    }
}
