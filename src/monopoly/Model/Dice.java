package monopoly.Model;

import java.util.Random;

public class Dice{
    private final Random randomNo;

    public Dice()
    {
        this.randomNo = new Random();
    }

    public int rollDice()
    {
        return randomNo.nextInt(Configs.DiceSideNumber)+1;
    }
}
