package seedu.planpal.modes.contacts;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.Storeable;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Represents a contact in the PlanPal application.
 */
public class Contact implements Editable, Storeable {
    private static final String CATEGORY_SEPARATOR = "/";
    private static final String CATEGORY_VALUE_SEPARATOR = ":";
    private static final String STORAGE_PATH = "./data/contacts/contacts.txt";
    private static final String CATEGORY_PATH = "./data/contacts/categories.txt";
    private String name;
    private String phone;
    private String email;
    private String commandDescription;
    private ArrayList<String> categories = new ArrayList<>();
    
    /**
     * Constructs a new Contact object by parsing the given description string. The description
     * string is split using the CATEGORY_SEPARATOR, and for each category (starting from
     * the second item in the array), it processes the category using the {@link #processEditFunction(String)} method.
     *
     * @param description A string containing the description of the contact, with different
     *                    categories separated by a CATEGORY_SEPARATOR.
     * @throws PlanPalExceptions If an error occurs while processing the categories.
     */
    public Contact(String description) throws PlanPalExceptions {
        setCommandDescription(description);
        String[] categories = description.split(CATEGORY_SEPARATOR);
        if (categories.length <= 1) {
            throw new IllegalCommandException();
        }
        assert categories.length >= 2: "Illegal command executed in contact";
        for (int categoryIndex = 1; categoryIndex < categories.length; categoryIndex++) {
            processEditFunction(categories[categoryIndex]);
        }
    }

    /**
     * Updates the command description by modifying the value of the specified category.
     * The method splits the current command description into its categories and updates the
     * category with the new value, if it exists.
     *
     * @param category The category whose value needs to be updated (e.g., "name").
     * @param val The new value for the specified category.
     *
     * @throws PlanPalExceptions If the input is incomplete or improperly formatted.
     * @throws IllegalCommandException If the specified category is not recognized.
     */
    private void setCommandDescription(String category, String val) throws PlanPalExceptions {
        boolean isCategory = false;
        for (String cat : ContactManager.INFORMATION_CATEGORIES) {
            if (category.equals(cat)) {
                isCategory = true;
                if (category.equals("email") &&
                        !val.trim().matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")) {
                    throw new PlanPalExceptions("email address is not valid");
                }
                try {
                    Field field = this.getClass().getDeclaredField(category);
                    field.setAccessible(true);
                    field.set(this, val);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new PlanPalExceptions(e.getMessage());
                }
            }
        }
        if (!isCategory) {
            throw new IllegalCommandException();
        }
        commandDescription = "";
        if (name != null) {
            commandDescription += CATEGORY_SEPARATOR + "name" + CATEGORY_VALUE_SEPARATOR + name + " ";
        }
        if (phone != null) {
            commandDescription += CATEGORY_SEPARATOR + "phone" + CATEGORY_VALUE_SEPARATOR + phone + " ";
        }
        if (email != null) {
            commandDescription += CATEGORY_SEPARATOR + "email" + CATEGORY_VALUE_SEPARATOR + email + " ";
        }
    }

    @Override
    public void setCommandDescription(String description) {
        this.commandDescription = description;
    }

    /**
     * Processes an edit command for the contact. This method parses the input string
     * to extract the category and the new value, then applies the change to the contact.
     * Currently, only the "name" category is supported.
     *
     * @param input The edit command in the format "category:value", where "category"
     *              specifies the field to edit (e.g., "name") and "value" specifies
     *              the new value for that field.
     * @throws PlanPalExceptions If the input is incomplete or improperly formatted.
     * @throws IllegalCommandException If the specified category is not recognized.
     */
    @Override
    public void processEditFunction(String input) throws PlanPalExceptions {
        if (input.isEmpty()) {
            throw new IllegalCommandException();
        }

        Ui.validateTags(input);
        String[] inputParts = input.split(CATEGORY_VALUE_SEPARATOR);
        if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
            throw new IllegalCommandException();
        }

        assert inputParts.length >= 2 : "Input must contain category and value";

        String category = inputParts[0].trim().toLowerCase();
        String valueToEdit = inputParts[1].trim();

        assert category != null && !category.isEmpty() : "Category cannot be null";
        assert valueToEdit != null && !valueToEdit.isEmpty() : "Value cannot be null";

        setCommandDescription(category, valueToEdit);
    }

    // A string representation of a Contact
    @Override
    public String toString() {
        return "[Name = " + name + ", Phone = " + phone + ", Email = "+ email + ", Categories = " + categories + "]";
    }

    @Override
    public String getCommandDescription() {
        return commandDescription;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getStoragePath() {
        return STORAGE_PATH;
    }

    public static String getCategoriesPath() {
        return CATEGORY_PATH;
    }

    public void clearCategories() {
        categories.clear();
    }

    public void editCategory(String category) {
        categories.add(category);
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
