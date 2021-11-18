import monopoly.Model.Configs;
import monopoly.Model.InPrisonState;
import monopoly.Model.Property;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import monopoly.Model.Player;

import static org.junit.jupiter.api.Assertions.*;

// Test the methods in class player
public class PlayerTest {

    static Player player1;
    static Player player2;
    static Property property1;
    @BeforeAll
    static void before(){
        player1 = new Player(0, 1, "Xavier");
        player2 = new Player(0, 2,
                "Eric", 0, false, "FREE");

        property1 = new Property(0,"", 200, 10);
    }

    @Test
    void onGoingGoTest(){
        // The money will be added when landing on square Go or pass
        assertTrue(player1.onGoingGo());
        assertEquals(Configs.initialFunding, player1.getMoney());
    }

    @Test
    void onGoingAndBuyingPropertyTest(){
        // return false because no host property won't need to pay rent
        assertFalse(player1.onGoingProperty(property1));
        // if the player doesn't have enough monet, he or she cannot buy a property
        assertFalse(player2.onBuyingProperty(property1));

        player2.setMoney(1500);
        // buying property will cost money
        player2.onBuyingProperty(property1);
        assertEquals(1500-property1.getPrice(), player2.getMoney());
        // the host of the property should be set
        assertTrue(property1.hasHost());
        assertEquals(player2.getPlayerID(), property1.getHostID());

        // Once the property has a host, the passengers need to pay rent to the host, but the host won't charge himself
        player2.onGoingProperty(property1);
        assertEquals(1500-property1.getPrice(), player2.getMoney());
        player1.setMoney(1500);
        assertTrue(player1.onGoingProperty(property1));
        assertEquals(Configs.initialFunding-property1.getRent(), player1.getMoney());

        // A property cannot be bought twice in one single Game
        try{
            player1.onBuyingProperty(property1);
            assert true;
        }
        catch (Exception exception){
            assert true;
        }
    }

    @Test
    void onGoingPrisonTest(){
        assertTrue(player1.onGoingPrison());
        assertEquals(InPrisonState.INJAILROUND1, player1.getPrisonState());
        assertTrue(player1.IsInPrison());
    }

    @Test
    void onStayingPrisonTest(){
        player2.setMoney(400);
    }

    @Test
    public void onGoingOtherTest(){
        assertTrue(player1.onGoingChance());
        assertTrue(player1.onGoingFreeParking());
        assertTrue(player1.onGoingJustVisiting());
    }

}
