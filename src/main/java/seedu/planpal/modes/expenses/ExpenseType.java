package seedu.planpal.modes.expenses;

public enum ExpenseType {
    FOOD, TRANSPORTATION, ENTERTAINMENT, OTHER;

    /**
     * Checks if a given string matches any enum constant (case-insensitive).
     *
     * @param value The string to check.
     * @return true if the value is a valid ExpenseType, false otherwise.
     */
    public static boolean isValidType(String value) {
        for (ExpenseType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
