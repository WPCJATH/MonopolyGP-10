import org.junit.jupiter.api.Test;

import static monopoly.Model.SquareType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SquareTypeTest {

    @Test
    public void parseTest(){
        // When load the Square type from database, the type should be parsed from string.
        assertEquals(GO, parseSquareType("GO"));

        assertEquals(INJAILORJUSTVISITING, parseSquareType("INJAILORJUSTVISITING"));

        assertNull(parseSquareType(""));
        assertNull(parseSquareType("injailorjust"));
        assertNull(parseSquareType("fguwgurfgwufg"));
    }
}
