package seedu.planpal.contacts;

import seedu.planpal.exceptions.IllegalCommandException;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.Editable;

/**
 * Represents a contact in the PlanPal application.
 */
public class Contact implements Editable {
    private static final String CATEGORY_SEPARATOR = "/";
    private static final String CATEGORY_VALUE_SEPARATOR = ":";
    private String name;

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
        String[] categories = description.split(CATEGORY_SEPARATOR);
        for (int categoryIndex = 1; categoryIndex < categories.length; categoryIndex++) {
            processEditFunction(categories[categoryIndex]);
        }
    }

    /**
     * Retrieves the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     *
     * @param name The new name for the contact. This should not be null.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if this contact's name contains the name of another contact.
     *
     * @param other The other contact to compare with.
     * @return True if this contact's name contains the other contact's name; false otherwise.
     */
    public boolean contains(Contact other) {
        return this.name.toLowerCase().contains(other.getName().toLowerCase());
    }

    @Override
    public void processEditFunction(String input) throws PlanPalExceptions {
        String[] inputParts = input.split(CATEGORY_VALUE_SEPARATOR);
        if (inputParts.length < 2) {
            throw new PlanPalExceptions("The command is incomplete. Please provide a value for " + inputParts[0]);
        }
        String category = inputParts[0].trim();
        String valueToEdit = inputParts[1].trim();
        if (category.equals("name")) {
            setName(valueToEdit);
        } else {
            System.out.println(category + " is not a valid category");
            throw new IllegalCommandException();
        }
    }
    /**
     * Returns a string representation of the contact.
     * This representation includes the name of the contact formatted in a readable form.
     *
     * @return A string representation of the contact, which includes the name.
     */
    @Override
    public String toString() {
        return "[Name = " + name + "]";
    }
}
