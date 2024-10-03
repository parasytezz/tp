package seedu.planpal.Contacts;

import seedu.planpal.PlanPalExceptions.EmptyDescriptionException;
import seedu.planpal.PlanPalExceptions.PlanPalExceptions;
import seedu.planpal.Utility.Functions;

import java.util.ArrayList;

public class ContactManager implements Functions<Contact> {
    
    private ArrayList<Contact> contactList = new ArrayList<>();
    
    public void addContact(String description) throws PlanPalExceptions {
        if (description.isEmpty()){
            throw new EmptyDescriptionException();
        }
        addToList(contactList, new Contact(description));
    }
}
