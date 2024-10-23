package seedu.planpal.contacts;

import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ContactManagerTest {
    @Test
    public void addContact_validFormat_success() {
        ContactManager manager = new ContactManager();
        try{
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");
            manager.addContact("/name:Charlie");
            manager.addContact("/name:Johnny");
            String expectedFormat = "[Name = %s, Phone Number = null, Email = null]";
            assertEquals(String.format(expectedFormat, "Alice"), manager.getContactList().get(0).toString());
            assertEquals(String.format(expectedFormat, "Bob"), manager.getContactList().get(1).toString());
            assertEquals(String.format(expectedFormat, "Charlie"), manager.getContactList().get(2).toString());
            assertEquals(String.format(expectedFormat, "Johnny"), manager.getContactList().get(3).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addContact_invalidFormat_fail() {
        ContactManager manager = new ContactManager();
        try{
            manager.addContact("Alice");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("No such commands exist. Do check what I can do again", e.getMessage());
        }
    }

    @Test
    public void addContact_invalidCategory_fail() {
        ContactManager manager = new ContactManager();
        try{
            manager.addContact("/name");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("The command is incomplete. Please provide a value for name", e.getMessage());
        }
    }

    @Test
    public void deleteContact_validFormat_success() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");
            manager.addContact("/name:Charlie");
            manager.addContact("/name:Johnny");

            manager.deleteContact("1");
            assertEquals("[Name = Bob, Phone Number = null, Email = null]", manager.getContactList().get(0).toString());
            assertEquals("[Name = Charlie, Phone Number = null, Email = null]", manager.getContactList().get(1).toString());
            assertEquals("[Name = Johnny, Phone Number = null, Email = null]", manager.getContactList().get(2).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteContact_invalidIndex_exceptionThrown() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.deleteContact("0");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("Invalid index. Please input a valid number.", e.getMessage());
        }
    }

    @Test
    public void deleteContact_emptyInput_exceptionThrown() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.deleteContact("");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void editContact_validIndex_success() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.editContact("1 /name: Alex");

            assertEquals("Alex", manager.getContactList().get(0).getName());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
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

    @Test
    public void findContact_validName_success() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");
            manager.addContact("/name:Charlie");

            manager.findContact("Alice");
            assertEquals("[Name = Alice, Phone Number = null, Email = null]", manager.getContactList().get(0).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findContact_multipleMatches_success() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice Lim");
            manager.addContact("/name:Alice Wong");
            manager.addContact("/name:Bob");

            manager.findContact("Alice Bob");
            assertEquals("[Name = Alice Lim, Phone Number = null, Email = null]", manager.getContactList().get(0).toString());
            assertEquals("[Name = Alice Wong, Phone Number = null, Email = null]", manager.getContactList().get(1).toString());
            assertEquals("[Name = Bob, Phone Number = null, Email = null]", manager.getContactList().get(2).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findContact_noMatches_exceptionThrown() {
        ContactManager manager = new ContactManager();
        try {
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.findContact("Charlie");
            fail();

        } catch (PlanPalExceptions e) {
            assertEquals("No matches found!", e.getMessage());
        }
    }

    @Test
    public void findContact_emptyDescription_exceptionThrown() {
        ContactManager manager = new ContactManager();
        try {
            manager.findContact("");
            fail();

        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }
}
