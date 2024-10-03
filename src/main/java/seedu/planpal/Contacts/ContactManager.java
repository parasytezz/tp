package seedu.planpal.Contacts;

import seedu.planpal.PlanPalExceptions.EmptyDescriptionException;
import seedu.planpal.PlanPalExceptions.PlanPalExceptions;
import seedu.planpal.Utility.Functions;

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
}
