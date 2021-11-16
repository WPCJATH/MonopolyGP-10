import monopoly.Model.Configs;
import monopoly.Model.Dice;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

// Test the dice: the right range; the uniform distribution
public class DiceTest {
    Dice dice = new Dice();
    int diceSides = Configs.DiceSideNumber;
    int[] diceNumbersStatistics = new int[diceSides];
    int testRounds = 100000;
    double tolerance = 0.002;

    @Test
    public void Test(){
        Arrays.fill(diceNumbersStatistics, 0);

        // Range Test
        for (int i=0; i<testRounds; i++)
        {
            int number = dice.rollDice();
            assert ((number > 0) && (number < diceSides+1));
            diceNumbersStatistics[number-1]++;
        }

        // Distribution Test, must be a uniform distribution with a small tolerance
        for (int num: diceNumbersStatistics){
            System.out.println((double)num/testRounds);
            assert (((double)num/testRounds > (double)1/diceSides - tolerance)
                    &&((double)num/testRounds < (double)1/diceSides + tolerance));
        }
    }

}
