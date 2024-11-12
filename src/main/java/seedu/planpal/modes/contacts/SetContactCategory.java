package seedu.planpal.modes.contacts;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class SetContactCategory implements ListFunctions {
    private ArrayList<Contact> contactList;
    private ArrayList<ArrayList<Contact>> contactListByCategory;
    private ArrayList<String> categoryList;
    private Logger contactLogger;

    /**
     * handle category setting commands of contacts
     * including : add (add category), remove (remove category),
     * edit (edit categories of contact), view (view available categories), quit
     *
     * @param description The description of the command
     * @param savedContacts The FileManager for saving category data
     * @param contactList ArrayList of contacts
     * @param contactListByCategory 2d ArrayList of Contact where i-th element  is the contacts in category of index i
     * @param categoryList ArrayList of Categories and their corresponding position in this array is their index
     * @param contactLogger logger for warnings or errors while processing commands
     *
     * @return true if continue to handle category commands. Otherwise, false.
     *
     * @throws PlanPalExceptions if fail to handle the command
     */
    public boolean handle(String description, FileManager savedContacts, ArrayList<Contact> contactList, 
                          ArrayList<ArrayList<Contact>> contactListByCategory, ArrayList<String> categoryList, 
                          Logger contactLogger) throws PlanPalExceptions {
        try {
            this.contactList = contactList;
            this.contactListByCategory = contactListByCategory;
            this.categoryList = categoryList;
            this.contactLogger = contactLogger;
            if (description.startsWith("add") &&
                    ( description.length() == 3 || description.charAt(3) == ' ')) {
                addCategory(description);
                savedContacts.saveCategories(contactList, contactListByCategory, categoryList);
                return true;
            } else if (description.startsWith("remove") &&
                    ( description.length() == 6 || description.charAt(6) == ' ')) {
                removeCategory(description);
                savedContacts.saveCategories(contactList, contactListByCategory, categoryList);
                return true;
            } else if (description.startsWith("edit") &&
                    ( description.length() == 4 || description.charAt(4) == ' ')) {
                editCategory(description);
                savedContacts.saveCategories(contactList, contactListByCategory, categoryList);
                return true;
            } else if (description.equals("view")) {
                viewList(categoryList);
                return true;
            } else if (description.equals("list")) {
                viewList(contactList);
                return true;
            } else if (description.equals("help")) {
                Ui.printCategoryMenu();
                return true;
            } else if (description.equals("exit")) {
                Ui.printCategoryExit();
                return false;
            } else {
                contactLogger.warning("Failed to set category: invalid command");
                throw new PlanPalExceptions("Invalid command");
            }
        } catch (PlanPalExceptions e) {
            throw e;
        }
    }

    /**
     * Helper function to handle adding of category
     *
     * @param description The description of the command
     * @throws EmptyDescriptionException If the description is empty, an {@link EmptyDescriptionException} is thrown.
     */
    private void addCategory(String description) throws PlanPalExceptions {
        String newCategory = description.replaceFirst("add", "").trim();
        if (newCategory.isEmpty()) {
            throw new EmptyDescriptionException();
        } else if (newCategory.contains("/")) {
            throw new PlanPalExceptions("/ is not allowed to be used in category name");
        } else if (categoryList.contains(newCategory)) {
            throw new PlanPalExceptions("Category already exists");
        } else {
            contactListByCategory.add(new ArrayList<Contact>());
            categoryList.add(newCategory);
            Ui.print("successfully added Category : '" + newCategory + "'");
        }
    }

    /**
     * Helper function to handle removal of category
     *
     * @param description The description of the command
     * @throws PlanPalExceptions If the input is invalid
     */
    private void removeCategory(String description) throws PlanPalExceptions {
        String removingCategory = description.replaceFirst("remove", "").trim();
        if (removingCategory.isEmpty()) {
            contactLogger.warning("Category is empty. Throwing EmptyDescriptionException.");
            throw new EmptyDescriptionException();
        } else if (!categoryList.contains(removingCategory)) {
            contactLogger.warning("Category is not found.");
            throw new PlanPalExceptions(removingCategory + " is not a category");
        } else {
            removeCategoryInContacts(removingCategory);
            contactListByCategory.remove(categoryList.indexOf(removingCategory));
            categoryList.remove(removingCategory);
            Ui.print("successfully deleted Category : '" + removingCategory + "'");
        }
    }

    /**
     * Helper function to handle removal of category in contacts
     *
     * @param category The category to be removed
     */
    private void removeCategoryInContacts(String category) {
        for (Contact contact : contactList) {
            contact.getCategories().remove(category);
        }
    }

    /**
     * Helper function to handle editing of categories of a contact
     *
     * @param description The description of the command
     * @throws PlanPalExceptions If the input is invalid
     */
    private void editCategory(String description) throws PlanPalExceptions {
        try {
            String descriptionToEdit = description.replaceFirst("edit", "").trim();
            String[] categories;
            String[] uniqueCategories = {};
            int contactId;
            if (descriptionToEdit.trim().isEmpty()) {
                throw new EmptyDescriptionException();
            } else if (descriptionToEdit.split(" ").length == 1) {
                contactId = Integer.parseInt(descriptionToEdit) - 1;
                categories = null;
            } else {
                String contactIdString = descriptionToEdit.substring(0, descriptionToEdit.indexOf(" ")).trim();
                String newCategories = descriptionToEdit.substring(descriptionToEdit.indexOf(" ")).trim();
                categories = Arrays.stream(newCategories.split("/")).map(String::trim).toArray(String[]::new);
                Set<String> uniqueSet = new HashSet<>(Arrays.asList(categories));
                uniqueCategories = uniqueSet.toArray(new String[0]);
                contactId = Integer.parseInt(contactIdString) - 1;
            }
            Contact editingContact = validateEdit(uniqueCategories, contactId);
            updateCategory(editingContact, uniqueCategories);
            Ui.print("successfully assigned categories to Contact id : " + (contactId + 1));
        } catch (NumberFormatException e) {
            contactLogger.warning("Invalid input.");
            throw new PlanPalExceptions("Invalid input.");
        }
    }

    /**
     * Helper function to validate editing categories of a contact
     *
     * @param categories array of new categories of the contact
     * @param contactId index of Contact in contactList
     * @return contact with index equals contactId
     * @throws PlanPalExceptions If the command is invalid
     */
    private Contact validateEdit(String[] categories, int contactId) throws PlanPalExceptions {
        if (contactId >= contactList.size() || contactId < 0) {
            throw new PlanPalExceptions("Invalid contact id");
        }
        if (categories == null) {
            return contactList.get(contactId);
        }
        for (String category : categories) {
            if (!categoryList.contains(category)) {
                contactLogger.warning("Category is not found.");
                throw new PlanPalExceptions(category + " is not a valid category");
            }
        }
        return contactList.get(contactId);
    }

    /**
     * Helper function to update categories of a contact
     *
     * @param editingContact The contact whose categories are to be edited
     * @param categories array of new categories of the contact
     */
    private void updateCategory(Contact editingContact, String[] categories) {
        ArrayList<String> originalCategories = editingContact.getCategories();
        for (String category : originalCategories) {
            int removingCategoryID = categoryList.indexOf(category);
            contactListByCategory.get(removingCategoryID).remove(editingContact);
        }
        editingContact.clearCategories();
        if (categories == null) {
            return;
        }
        for (String category : categories) {
            if (!categoryList.contains(category)) {
                continue;
            } else {
                contactListByCategory.get(categoryList.indexOf(category)).add(editingContact);
                editingContact.editCategory(category);
            }
        }
    }
    
}
