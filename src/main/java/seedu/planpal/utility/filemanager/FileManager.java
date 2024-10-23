package seedu.planpal.utility.filemanager;

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

    /**
     * Ensures that the directory for the storage path exists.
     * If the directory does not exist, it creates it.
     */
    private void createDirectory(String storagePath){
        File file = new File(storagePath);
        File directory = file.getParentFile();

        if (!directory.exists()){
            if (directory.mkdirs()){
                System.out.println("Directory created");
            } else {
                System.out.println("Directory could not be created");
            }
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
        T listElement = list.get(0);
        if (listElement instanceof Storeable) {
            String storagePath = ((Storeable)listElement).getStoragePath();
            createDirectory(storagePath);
            try(FileWriter writer = new FileWriter(storagePath)){
                for (T item : list) {
                    String commandDescription = ((Storeable) item).getCommandDescription();
                    writer.write(ADD_COMMAND + " " + commandDescription + "\n");
                }
            } catch (IOException e) {
                Ui.print("Error saving data!");
            }
        }
    }

    /**
     * Loads a list of objects from each corresponding file in storage.
     * The file is parsed line by line, and each command is processed using the provided {@link Parser}.
     *
     * @param parser A {@link Parser} object used to process each line of the file.
     */
    public void loadLists(Parser parser, String directoryName) {
        File directory = new File(directoryName);
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
