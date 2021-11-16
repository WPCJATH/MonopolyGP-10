import monopoly.Model.ChanceSquare;
import monopoly.Model.Configs;
import monopoly.Model.IncomeTaxSquare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
* The test of squares.
*
* Considering that most the operation functions are put in class Player and all judgement method are put
* in the Controller, so that the square doesn't have so many methods to test
* */
public class SquaresTest {

    @Test
    public void taxCalculationTest(){
        // Calculate the income tax based on players' money
        assertEquals(10, IncomeTaxSquare.calculateTax(100));
        assertEquals(0, IncomeTaxSquare.calculateTax(10));
        assertEquals(50, IncomeTaxSquare.calculateTax(564));
        assertEquals(10, IncomeTaxSquare.calculateTax(128));
        assertEquals(12340, IncomeTaxSquare.calculateTax(123456));
        assertEquals(0, IncomeTaxSquare.calculateTax(0));
    }

    @Test
    public void luckyDrawTest(){
        // The lucky draw of Chance square. 6 card will pass to the on-going player and the player need to pick one.
        // The six int are the money on the 6 cards, here to test whether the 6 numbers are in the required range
        ChanceSquare chanceSquare = new ChanceSquare(0);

        int[] reNums;
        for (int i=0; i<1000;i++){
            reNums = chanceSquare.luckyDraw();
            assertEquals(4, reNums.length);
            for (int element: reNums){
                assert (element >= Configs.minimumChanceMoney);
                assert (element <= Configs.maximumChanceMoney);
            }
        }
    }
}
