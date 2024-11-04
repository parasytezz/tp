package seedu.planpal.contacts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.contacts.Contact;
import seedu.planpal.modes.contacts.ContactManager;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactManagerTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void addContact_validFormat_success() {
        try{
            ContactManager manager = new ContactManager();
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");
            manager.addContact("/name:Charlie");
            manager.addContact("/name:Johnny");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(0).toString());
            assertEquals("[Name = Bob, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(1).toString());
            assertEquals("[Name = Charlie, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(2).toString());
            assertEquals("[Name = Johnny, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(3).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addContact_invalidFormat_fail() {
        try{
            ContactManager manager = new ContactManager();
            manager.addContact("Alice");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("No such commands exist. Do check what I can do again", e.getMessage());
        }
    }

    @Test
    public void addContact_invalidField_fail() {
        try{
            ContactManager manager = new ContactManager();
            manager.addContact("/name");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("The command is incomplete. Please provide a value for name", e.getMessage());
        }
    }

    @Test
    public void deleteContact_validFormat_success() {
        try {
            ContactManager manager = new ContactManager();
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");
            manager.addContact("/name:Charlie");
            manager.addContact("/name:Johnny");

            manager.deleteContact("1");
            assertEquals("[Name = Bob, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(0).toString());
            assertEquals("[Name = Charlie, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(1).toString());
            assertEquals("[Name = Johnny, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(2).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteContact_invalidIndex_exceptionThrown() {
        try {
            ContactManager manager = new ContactManager();
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");

            manager.deleteContact("0");
            fail();
        } catch (PlanPalExceptions e) {
            assertEquals("Invalid Index!", e.getMessage());
        }
    }

    @Test
    public void deleteContact_emptyInput_exceptionThrown() {
        try {
            ContactManager manager = new ContactManager();
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
        try {
            ContactManager manager = new ContactManager();
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
        try {
            ContactManager manager = new ContactManager();
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
        try {
            ContactManager manager = new ContactManager();
            manager.addContact("/name:Alice");

            // Attempt to edit contact with empty query
            manager.editContact("");
            fail(); // Test should not reach this line if exception is thrown
        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void findContact_validName_success() {
        try {
            ContactManager manager = new ContactManager();
            manager.addContact("/name:Alice");
            manager.addContact("/name:Bob");
            manager.addContact("/name:Charlie");

            manager.findContact("Alice");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(0).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findContact_multipleMatches_success() {
        try {
            ContactManager manager = new ContactManager();
            manager.addContact("/name:Alice Lim");
            manager.addContact("/name:Alice Wong");
            manager.addContact("/name:Bob");

            manager.findContact("Alice Bob");
            assertEquals("[Name = Alice Lim, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(0).toString());
            assertEquals("[Name = Alice Wong, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(1).toString());
            assertEquals("[Name = Bob, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(2).toString());

        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findContact_noMatches_exceptionThrown() {
        try {
            ContactManager manager = new ContactManager();
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
        try {
            ContactManager manager = new ContactManager();
            manager.findContact("");
            fail();

        } catch (PlanPalExceptions e) {
            assertEquals("Description cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void addCategory_success() {
        ContactManager manager = new ContactManager();
        assertTrue(manager.handleCategory("add        emergency       "));
        assertTrue(manager.handleCategory("add friend"));
        assertTrue(manager.handleCategory("add family"));
        ArrayList<String> expectedCategories = new ArrayList<>();
        ArrayList<ArrayList<Contact>> expectedContactListByCategory = new ArrayList<>();
        expectedCategories.add("emergency");
        expectedContactListByCategory.add(new ArrayList<>());
        expectedCategories.add("friend");
        expectedContactListByCategory.add(new ArrayList<>());
        expectedCategories.add("family");
        expectedContactListByCategory.add(new ArrayList<>());
        assertEquals(expectedCategories, manager.getCategoryList());
        assertEquals(expectedContactListByCategory, manager.getContactListByCategory());
    }

    @Test
    public void addCategory_invalidCategoryName() {
        ContactManager manager = new ContactManager();
        manager.handleCategory("add em/ergency");
        String output = "_________________________________________________________\n" +
                "/ is not allowed to be used in category name\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        OUTPUT_STREAM.reset();
        manager.handleCategory("add  / qouvblo");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        OUTPUT_STREAM.reset();
        manager.handleCategory("add  qoue  wg w / wvwvblo");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

    @Test
    public void addCategory_emptyDescription() {
        ContactManager manager = new ContactManager();
        manager.handleCategory("add");
        String output = "_________________________________________________________\n" +
                "Description cannot be empty!\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        OUTPUT_STREAM.reset();
        manager.handleCategory("add       ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

    @Test
    public void removeCategory_success() {
        ContactManager manager = new ContactManager();
        assertTrue(manager.handleCategory("add    emergency"));
        assertTrue(manager.handleCategory("add friend  "));
        assertTrue(manager.handleCategory("add    family  "));
        assertTrue(manager.handleCategory("remove    friend    "));
        ArrayList<String> expectedCategories = new ArrayList<>();
        ArrayList<ArrayList<Contact>> expectedContactListByCategory = new ArrayList<>();
        expectedCategories.add("emergency");
        expectedContactListByCategory.add(new ArrayList<>());
        expectedCategories.add("family");
        expectedContactListByCategory.add(new ArrayList<>());
        assertEquals(expectedCategories, manager.getCategoryList());
        assertEquals(expectedContactListByCategory, manager.getContactListByCategory());
    }

    @Test
    public void removeCategory_noMatches() {
        ContactManager manager = new ContactManager();
        assertTrue(manager.handleCategory("add emergency"));
        assertTrue(manager.handleCategory("add family"));
        OUTPUT_STREAM.reset();
        assertTrue(manager.handleCategory("remove    friend  "));
        String output = "_________________________________________________________\n" +
                "friend is not a category\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

    @Test
    public void removeCategory_emptyDescription() {
        ContactManager manager = new ContactManager();
        manager.handleCategory("remove");
        String output = "_________________________________________________________\n" +
                "Description cannot be empty!\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        manager.handleCategory("remove       ");
        assertEquals(trimmedExpectedOutput+trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

    @Test
    public void editCategory_singleCategory_success() {
        try {
            ContactManager manager = new ContactManager();
            manager.handleCategory("add emergency");
            manager.addContact("/name:Alice");
            manager.handleCategory("edit 1 emergency");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = [emergency]]",
                    manager.getContactList().get(0).toString());
            manager.handleCategory("edit 1    ");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(0).toString());
            manager.addContact("/name:Bob");
            manager.handleCategory("edit 2 emergency");
            assertEquals("[Name = Bob, Phone = null, Email = null, Categories = [emergency]]",
                    manager.getContactList().get(1).toString());
            manager.handleCategory("edit 2");
            assertEquals("[Name = Bob, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(1).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editCategory_multipleCategory_success() {
        try {
            ContactManager manager = new ContactManager();
            manager.handleCategory("add emergency");
            manager.handleCategory("add family");
            manager.handleCategory("add friend");
            manager.addContact("/name:Alice");
            manager.handleCategory("edit 1       emergency/      family   /        friend");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = [emergency, family, friend]]",
                    manager.getContactList().get(0).toString());
            manager.handleCategory("edit 1    ");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = []]",
                    manager.getContactList().get(0).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editCategory_emptyDescription() {
        ContactManager manager = new ContactManager();
        manager.handleCategory("edit");
        String output = "_________________________________________________________\n" +
                "Description cannot be empty!\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        OUTPUT_STREAM.reset();
        manager.handleCategory("edit       ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

    @Test
    public void editCategory_invalidIndex() {
        try {
            ContactManager manager = new ContactManager();
            manager.handleCategory("add emergency");
            manager.handleCategory("add family");
            manager.handleCategory("add friend");
            manager.addContact("/name:Alice");
            OUTPUT_STREAM.reset();
            manager.handleCategory("edit 0 emergency/family/friend");
            String output = "_________________________________________________________\n" +
                    "Invalid contact id\n" +
                    "_________________________________________________________\n";
            String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            OUTPUT_STREAM.reset();
            manager.handleCategory("edit -1 emergency    ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            OUTPUT_STREAM.reset();
            manager.handleCategory("edit 2    emergency    ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            OUTPUT_STREAM.reset();
            manager.handleCategory("edit    emergency    ");
            output = "_________________________________________________________\n" +
                    "Invalid input.\n" +
                    "_________________________________________________________\n";
            trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void editCategory_invalidCategory() {
        try {
            ContactManager manager = new ContactManager();
            manager.handleCategory("add emergency");
            manager.handleCategory("add family");
            manager.handleCategory("add friend");
            manager.addContact("/name:Alice");
            OUTPUT_STREAM.reset();
            manager.handleCategory("edit 1       family/     em  ergency  /        friend");
            String output = "_________________________________________________________\n" +
                    "em ergency is not a valid category\n" +
                    "_________________________________________________________\n";
            String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            OUTPUT_STREAM.reset();
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void removeCategoryInContact_success() {
        try {
            ContactManager manager = new ContactManager();
            manager.handleCategory("add    emergency");
            manager.handleCategory("add friend  ");
            manager.handleCategory("add    family  ");
            manager.addContact("/name:Alice");
            manager.handleCategory("edit 1 emergency/family/friend");
            manager.handleCategory("remove family  ");
            assertEquals("[Name = Alice, Phone = null, Email = null, Categories = [emergency, friend]]",
                    manager.getContactList().get(0).toString());
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void category_invalidCommand() {
        ContactManager manager = new ContactManager();
        manager.handleCategory("ahhhhhhh");
        String output = "_________________________________________________________\n" +
                "Invalid command\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        OUTPUT_STREAM.reset();
        manager.handleCategory("view   ");
        output = "_________________________________________________________\n" +
                "Invalid command\n" +
                "_________________________________________________________\n";
        trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

    @Test
    public void viewCategory_success() {
        ContactManager manager = new ContactManager();
        manager.handleCategory("view");
        String output = "_________________________________________________________\n" +
                "_________________________________________________________\n";
        String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        assertTrue(manager.handleCategory("add emergency"));
        assertTrue(manager.handleCategory("add family"));
        OUTPUT_STREAM.reset();
        assertTrue(manager.handleCategory("view"));
        output = "_________________________________________________________\n" +
                "emergency\n" +
                "family\n" +
                "_________________________________________________________\n";
        trimmedExpectedOutput = output.replaceAll("\\s+", " ");
        assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
    }

}
