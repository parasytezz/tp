package seedu.planpal.Utility;

import seedu.planpal.Contacts.Contact;

import java.util.ArrayList;

public interface Functions<T> {
    public static final String LINE_SEPARATOR = "_________________________________________________________";

    static void print(String... messages){
        System.out.println(LINE_SEPARATOR);
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println(LINE_SEPARATOR);
    }

    default void addToList(ArrayList<T> list, T element){
        list.add(element);
        print("Added successfully!");
    }

    default void viewList(ArrayList<T> list){
        System.out.println(LINE_SEPARATOR);
        System.out.println("Below is the list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i).toString());
        }
        System.out.println(LINE_SEPARATOR);
    }
}
