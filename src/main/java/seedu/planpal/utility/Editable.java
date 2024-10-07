package seedu.planpal.utility;

import seedu.planpal.exceptions.PlanPalExceptions;

/**
 * Represents an editable object that can process an edit command.
 */
public interface Editable {
    /**
     * Processes an edit command using the provided values.
     *
     * @param newValues An array of new values that should be used to edit the object.
     * @throws PlanPalExceptions if errors occur
     */
    void processEditFunction(String newValues) throws PlanPalExceptions;
}
