package seedu.planpal;

import org.junit.jupiter.api.Test;
import seedu.planpal.exceptions.InvalidModeException;
import seedu.planpal.utility.parser.Parser;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanPalTest {

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void validModeTest() {
        assertThrows(NoSuchElementException.class, () -> new Parser().processCommand("1"));
        assertThrows(NoSuchElementException.class, () -> new Parser().processCommand("2"));
        assertThrows(NoSuchElementException.class, () -> new Parser().processCommand("3"));
    }

    @Test
    public void invalidModeTest() {
        assertThrows(InvalidModeException.class, () -> new Parser().processCommand("15"));
        assertThrows(InvalidModeException.class, () -> new Parser().processCommand("-7"));
        assertThrows(InvalidModeException.class, () -> new Parser().processCommand("0.1"));
    }

}
