import org.junit.jupiter.api.Test;

import static monopoly.Model.InPrisonState.*;
import static org.junit.jupiter.api.Assertions.*;

// Test the enum class InPrisonState
public class InPrisonStateTest {
    @Test
    public void NextStateTest(){
        // the states can be cycled through because a play can be in jail almost 3 continues round
        assertEquals(INJAILROUND1,goNextState(FREE));
        assertEquals(INJAILROUND2,goNextState(INJAILROUND1));
        assertEquals(INJAILROUND3,goNextState(INJAILROUND2));
        assertEquals(FREE,goNextState(INJAILROUND3));
    }

    @Test
    public void isInJailTest(){
        // only free is not a in jail state
        assertFalse(isInJail(FREE));
        assertTrue(isInJail(INJAILROUND1));
        assertTrue(isInJail(INJAILROUND2));
        assertTrue(isInJail(INJAILROUND3));
    }

    @Test
    public void parseTest(){
        // when load data from database, the state should be transferred from String format
        assertEquals(FREE, parseState("FREE"));

        assertEquals(INJAILROUND1, parseState("INJAILROUND1"));

        assertNull(parseState(""));
        assertNull(parseState("INJAILROUND"));
    }
}
