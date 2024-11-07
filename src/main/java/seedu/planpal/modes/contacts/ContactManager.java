package seedu.planpal.modes.contacts;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.ListFunctions;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.FileManager;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the contact list within the PlanPal application.
 */
public class ContactManager implements ListFunctions {
    public static final String[] INFORMATION_CATEGORIES = {"name", "phone", "email"};
    private static final Logger CONTACT_LOGGER = Logger.getLogger(ContactManager.class.getName());
    FileManager savedContacts = new FileManager();
    SetContactCategory setContactCategory = new SetContactCategory();
    private ArrayList<Contact> contactList = new ArrayList<>();
    private ArrayList<ArrayList<Contact>> contactListByCategory = new ArrayList<ArrayList<Contact>>();
    private ArrayList<String> categoryList = new ArrayList<>();

    public ContactManager() {
        CONTACT_LOGGER.setLevel(Level.SEVERE);
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    /**
     * Adds a new contact to the contact list.
     * The contact is created from the provided description.
     *
     * @param description The description of the new contact. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} is thrown.
     */
    public void addContact(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }
        addToList(contactList, new Contact(description));
        CONTACT_LOGGER.info("Added contact");
        savedContacts.saveList(contactList);
    }

    /**
     * Deletes an existing contact from the contact list.
     * The contact is retrieved from its description.
     *
     * @param index The description of the contact to be deleted. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} os thrown.
     */
    public void deleteContact(String index) throws PlanPalExceptions {
        if (index.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        assert index.length() != 0 : "Index must not be empty";

        CONTACT_LOGGER.info("Deleting contact with the index: " + index);
        try {
            boolean hasTwoBeforeDelete = (contactList.size() == 2);
            deleteList(contactList, index);
            savedContacts.saveList(contactList, hasTwoBeforeDelete);
            CONTACT_LOGGER.info("Deleted contact");
        } catch (PlanPalExceptions e) {
            CONTACT_LOGGER.severe("Failed to delete a contact: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Displays the entire list of contacts.
     * Each contact is printed with an index number for easy reference.
     */
    public void viewContactList(){
        viewList(contactList);
    }

    /**
     * Edits a contact in the contact list based on the provided query.
     * The query should contain the index of the contact to be edited, followed by the fields to update.
     *
     * @param query The query containing the index and new values for the contact.
     * @throws PlanPalExceptions If the index is out of bounds or other editing errors occur.
     */
    public void editContact(String query) throws PlanPalExceptions {
        CONTACT_LOGGER.info("Editing contact with query: " + query);

        try {
            editList(contactList, query);
            savedContacts.saveList(contactList);
            CONTACT_LOGGER.info("Edited contact successfully");
        } catch (PlanPalExceptions e) {
            CONTACT_LOGGER.severe("Failed to edit contact: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Finds contacts in the contact list based on the provided description.
     * The description can contain multiple names separated by spaces.
     * If matching contacts are found, they are displayed to the user.
     *
     * @param description The description of the contacts to find. This must not be empty.
     * @throws PlanPalExceptions If the description is empty, an {@link EmptyDescriptionException} is thrown.
     */
    public void findContact(String description) throws PlanPalExceptions {
        CONTACT_LOGGER.info("Searching for contacts with description: " + description);

        if (description.isEmpty()) {
            CONTACT_LOGGER.warning("Search description is empty. Throwing EmptyDescriptionException.");
            throw new EmptyDescriptionException();
        }

        assert description != null : "Description must not be null";
        assert !description.isEmpty() : "Description must not be empty";

        try {
            findInList(contactList, description);
            CONTACT_LOGGER.info("Search completed successfully.");
        } catch (PlanPalExceptions e) {
            CONTACT_LOGGER.warning("Failed to find contacts: " + e.getMessage());
            throw e;
        }
    }

    /**
     * handle category setting commands of contacts
     * including : add (add category), remove (remove category),
     * edit (edit categories of contact), view (view available categories), quit
     *
     * @param description The description of the command
     * @param out
     * @return true if continue to handle category commands. Otherwise, false.
     */
    public boolean handleCategory(String description, PrintStream out) {
        boolean inSetting = true;
        try {
            inSetting = setContactCategory.handle(description, savedContacts, contactList,
                                                contactListByCategory, categoryList, CONTACT_LOGGER);
        } catch (PlanPalExceptions e) {
            CONTACT_LOGGER.warning("Failed to set category: " + e.getMessage());
            Ui.setMainStream(out);
            Ui.print(e.getMessage());
        }
        return inSetting;
    }

    /**
     * Search contacts of a category
     *
     * @param description The category to be found
     */
    public void searchCategory(String description) {
        for (String category : categoryList) {
            if (category.equals(description)) {
                Ui.printCategory(category, contactListByCategory, categoryList);
                return;
            }
        }
        Ui.printCategoryNotFound();
    }

    /**
     * return categoryList
     * for testing use only
     *
     */
    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

    /**
     * return categoryListByCategory
     * for testing use only
     *
     */
    public ArrayList<ArrayList<Contact>> getContactListByCategory() {
        return contactListByCategory;
    }

}
