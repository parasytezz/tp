package seedu.planpal.Utility;

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


}
