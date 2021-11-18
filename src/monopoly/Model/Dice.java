package monopoly.Model;

import java.util.Random;

/**
 * The uniform random number dice generator
 */
public class Dice{
    private final Random randomNo;

    /**
     * Create am instance of a dice because we need a Random type variable
     */
    public Dice()
    {
        this.randomNo = new Random();
    }


    /**
     * @return the random number when throwing a dice
     */
    public int rollDice()
    {
        return randomNo.nextInt(Configs.DiceSideNumber)+1;
    }
}
