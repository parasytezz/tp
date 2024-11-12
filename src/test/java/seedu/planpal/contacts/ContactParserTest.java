package seedu.planpal.contacts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.utility.parser.modeparsers.ContactParser;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class ContactParserTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
        OUTPUT_STREAM.reset();
    }

    @Test
    public void searchCategory_validCategoryWithContact() {
        try {
            ContactManager manager = new ContactManager();
            ContactParser contactParser = new ContactParser(manager);
            manager.handleCategory("add emergency", System.out);
            manager.handleCategory("add family", System.out);
            manager.handleCategory("add friend", System.out);
            manager.addContact("/name:Alice");
            manager.handleCategory("edit 1       emergency/      family   /        friend", System.out);
            OUTPUT_STREAM.reset();
            contactParser.processCommand("search emergency");
            String output = "Contacts in category: emergency\n" +
                    "[Name = Alice, Phone = null, Email = null, Categories = [friend, emergency, family]]\n" +
                    "_________________________________________________________\n";
            String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            manager.handleCategory("edit 1    ", System.out);
            manager.addContact("/name:Bob");
            manager.handleCategory("edit 2  family   /        friend", System.out);
            manager.addContact("/name:Si Thu");
            manager.addContact("/name:Nathan");
            manager.addContact("/name:Gavin");
            manager.addContact("/name:yin shuang");
            manager.addContact("/name:Andy");
            manager.handleCategory("edit 4    emergency  ", System.out);
            manager.handleCategory("edit 3 friend", System.out);
            manager.handleCategory("edit 5 emergency", System.out);
            manager.handleCategory("edit 6 friend", System.out);
            OUTPUT_STREAM.reset();
            contactParser.processCommand("search      emergency");
            output = "Contacts in category: emergency\n" +
                    "[Name = Nathan, Phone = null, Email = null, Categories = [emergency]]\n" +
                    "[Name = Gavin, Phone = null, Email = null, Categories = [emergency]]\n" +
                    "_________________________________________________________\n";
            trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            OUTPUT_STREAM.reset();
            contactParser.processCommand("search friend");
            output = "Contacts in category: friend\n" +
                    "[Name = Bob, Phone = null, Email = null, Categories = [friend, family]]\n" +
                    "[Name = Si Thu, Phone = null, Email = null, Categories = [friend]]\n" +
                    "[Name = yin shuang, Phone = null, Email = null, Categories = [friend]]\n" +
                    "_________________________________________________________\n";
            trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void searchCategory_categoryNotFound() {
        try {
            ContactManager manager = new ContactManager();
            ContactParser contactParser = new ContactParser(manager);
            manager.handleCategory("add emergency", System.out);
            manager.handleCategory("add family", System.out);
            manager.handleCategory("add friend", System.out);
            manager.addContact("/name:Alice");
            manager.handleCategory("edit 1       emergency/      family   /        friend", System.out);
            manager.addContact("/name:Bob");
            manager.handleCategory("edit 2  family   /        friend", System.out);
            manager.addContact("/name:Si Thu");
            manager.addContact("/name:Nathan");
            manager.addContact("/name:Gavin");
            manager.addContact("/name:yin shuang");
            manager.addContact("/name:Andy");
            manager.handleCategory("edit 4    emergency  ", System.out);
            manager.handleCategory("edit 3 friend", System.out);
            manager.handleCategory("edit 5 emergency", System.out);
            manager.handleCategory("edit 6 friend", System.out);
            manager.handleCategory("remove emergency    ", System.out);
            OUTPUT_STREAM.reset();
            contactParser.processCommand("search emergency     ");
            String output = "Category not found.\n" +
                    "_________________________________________________________\n";
            String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void searchCategory_validCategoryWithNoContact() {
        try {
            ContactManager manager = new ContactManager();
            ContactParser contactParser = new ContactParser(manager);
            manager.handleCategory("add emergency", System.out);
            manager.handleCategory("add family", System.out);
            manager.handleCategory("add friend", System.out);
            manager.addContact("/name:Alice");
            manager.handleCategory("edit 1       emergency/      family   /        friend", System.out);
            OUTPUT_STREAM.reset();
            contactParser.processCommand("search emergency");
            String output = "Contacts in category: emergency\n" +
                    "[Name = Alice, Phone = null, Email = null, Categories = [friend, emergency, family]]\n" +
                    "_________________________________________________________\n";
            String trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
            manager.handleCategory("edit 1    ", System.out);
            OUTPUT_STREAM.reset();
            contactParser.processCommand("search      emergency");
            output = "Contacts in category: emergency\n" +
                    "There is no contact in emergency\n" +
                    "_________________________________________________________\n";
            trimmedExpectedOutput = output.replaceAll("\\s+", " ");
            assertEquals(trimmedExpectedOutput, OUTPUT_STREAM.toString().replaceAll("\\s+", " "));
        } catch (PlanPalExceptions e) {
            fail(e.getMessage());
        }
    }
}
