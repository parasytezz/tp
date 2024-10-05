package seedu.planpal.utility;

import java.util.ArrayList;
import seedu.planpal.contacts.Contact;

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
     * Searches for contacts in the provided list that match any of the specified elements.
     * It prints the matching contacts along with their respective indices.
     *
     * @param list The list of contacts to search in. This should not be null.
     * @param elements The array of contact names to search for. This must not be null and should contain at least one name.
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

