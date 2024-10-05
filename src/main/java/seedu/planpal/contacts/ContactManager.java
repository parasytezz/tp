package seedu.planpal.contacts;

import seedu.planpal.exceptions.EmptyDescriptionException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Functions;

import java.util.ArrayList;

/**
 * Manages the contact list within the PlanPal application.
 */
public class ContactManager implements Functions<Contact> {
    
    private ArrayList<Contact> contactList = new ArrayList<>();

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
    }

    /**
     * Displays the entire list of contacts.
     * Each contact is printed with an index number for easy reference.
     */
    public void viewContactList(){
        viewList(contactList);
    }

    /**
     * Edits an existing contact in the contact list.
     *
     * @param index The index of the contact in the list. It must be within the valid range of the contact list.
     * @param newName The new name of the contact. This must not be empty.
     * @throws PlanPalExceptions If the index does not exist.
     * @throws EmptyDescriptionException If the new name is empty.
     */
    public void editContact(int index, String newName) throws PlanPalExceptions {
        if (index < 1 || index > contactList.size()) {
            throw new PlanPalExceptions(
                    "Invalid index. The are " + contactList.size() + " contacts."
            );
        }
        if (newName.isEmpty()){
            throw new EmptyDescriptionException();
        }

        Contact contactToEdit = contactList.get(index - 1);
        contactToEdit.setName(newName);
        Functions.print("Contact successfully updated.");
    }
}
