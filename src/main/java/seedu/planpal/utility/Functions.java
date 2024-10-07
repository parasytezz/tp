package seedu.planpal.utility;

import java.util.ArrayList;
import seedu.planpal.contacts.Contact;
import seedu.planpal.exceptions.PlanPalExceptions;

/**
 * A utility interface providing generic list operations
 * applicable to various types of objects.
 * The methods are designed to be generic to enable
 * reuse across different types of data.
 *
 * @param <T> the type of elements managed by this interface
 */
public interface Functions<T> {
    public static final String LINE_SEPARATOR = "_________________________________________________________";

    /**
     * Prints a series of messages enclosed between line separators.
     * This method is useful for clear and structured display of information in the console.
     *
     * @param messages an array of messages to be printed; each message is printed on a new line
     */
    static void print(String... messages){
        System.out.println(LINE_SEPARATOR);
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Adds an element to the provided list and prints a success message.
     * This default method can be overridden by implementing classes if custom behavior is needed.
     *
     * @param list the list to which the element should be added
     * @param element the element to be added to the list
     */
    default void addToList(ArrayList<T> list, T element){
        list.add(element);
        print("Added successfully!");
    }

    /**
     * Displays all elements in the provided list, each prefixed by its index in the list.
     *
     * @param list the list whose contents are to be displayed
     */
    default void viewList(ArrayList<T> list){
        System.out.println(LINE_SEPARATOR);
        System.out.println("Below is the list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i).toString());
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Edits an element in the provided list based on the query input.
     * The query specifies the index of the element to edit and the new values.
     * If the element supports editing, the changes are applied, and a success message is printed.
     *
     * @param list the list containing the element to be edited
     * @param query the query containing the index and new values to apply
     * @throws PlanPalExceptions if the index is out of bounds
     */
    default void editList(ArrayList<T> list, String query) throws PlanPalExceptions {
        String[] toEdit = query.split("\\s+", 2);
        int index = Integer.parseInt(toEdit[0].trim());
        String[] newValues = toEdit[1].split("/");

        if (index < 1 || index > list.size()) {
            throw new PlanPalExceptions(
                    "Invalid index. The are " + list.size() + " items."
            );
        }

        T element = list.get(index - 1);

        if (element instanceof Editable) {
            for (int i = 1; i < newValues.length; i++) {
                ((Editable) element).processEditFunction(newValues[i]);
            }
        }
        print("Edited successfully!");
    }

    /**
     * Searches for contacts in the provided list that match any of the specified elements.
     * It prints the matching contacts along with their respective indices.
     *
     * @param list The list of contacts to search in. This should not be null.
     * @param elements The array of contact names to search for. This should not be null.
     */
    default void findInList(ArrayList<Contact> list, String[] elements){
        System.out.println(LINE_SEPARATOR);

        ArrayList<Contact> matchingContacts = new ArrayList<>();

        for (Contact contact : list) {
            for (String name : elements) {
                if (contact.contains(new Contact(name))) {
                    matchingContacts.add(contact);
                    break;
                }
            }
        }

        if (matchingContacts.isEmpty()) {
            System.out.println("No Contacts found");
        } else {
            System.out.println("Here is what I found:");
            for (int j = 0; j < matchingContacts.size(); j++) {
                System.out.println((j + 1) + ". " + matchingContacts.get(j).toString());
            }
        }

        System.out.println(LINE_SEPARATOR);
    }
}

