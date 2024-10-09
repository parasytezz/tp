package seedu.planpal.contacts;

import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ContactManagerTest {
    @Test
    public void editContact_validIndex_success() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.editContact("1 /name: Alex");

            assertEquals("Alex", manager.getContactList().get(0).getName());
        } catch (PlanPalExceptions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editContact_invalidIndex_exceptionThrown() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.editContact(("3 /name: Zoe"));
            fail(); // Test should not reach this line if exception is thrown
        } catch (PlanPalExceptions e) {
            assertEquals("Invalid index. There are 2 items.", e.getMessage());
        }
    }

    @Test
    public void editContact_emptyQuery_exceptionThrown() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");

            // Attempt to edit contact with empty query
            manager.editContact("");
            fail(); // Test should not reach this line if exception is thrown
        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty.", e.getMessage());
        }
    }
}
