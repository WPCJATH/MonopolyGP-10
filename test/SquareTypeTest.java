import org.junit.jupiter.api.Test;

import static monopoly.Model.SquareType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SquareTypeTest {

    @Test
    public void parseTest(){
        // When load the Square type from database, the type should be parsed from string.
        assertEquals(GO, parseSquareType("GO"));
        assertEquals(GO, parseSquareType("Go"));
        assertEquals(GO, parseSquareType("go"));

        assertEquals(INJAILORJUSTVISITING, parseSquareType("InJailOrJustVisiting"));
        assertEquals(INJAILORJUSTVISITING, parseSquareType("In Jail Or Just Visiting"));
        assertEquals(INJAILORJUSTVISITING, parseSquareType("in jail or just visiting   "));

        assertNull(parseSquareType(""));
        assertNull(parseSquareType("injailorjust"));
        assertNull(parseSquareType("fguwgurfgwufg"));
    }
}
