package seedu.planpal.contacts;

/**
 * Represents a contact in the PlanPal application.
 */
public class Contact {
    private String name;

    /**
     * Constructs a new Contact with the specified name.
     *
     * @param name The name of the contact. This should not be null.
     */
    public Contact(String name) {
        this.name = name;
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
