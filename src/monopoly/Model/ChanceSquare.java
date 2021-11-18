package monopoly.Model;

import java.util.Random;

/**
 * The backend of Chance Square. Mainly used to store the data of a Chance square.
 */
public class ChanceSquare extends SquareBackend {
    private final Random randomNo;

    /**
     * @param positionID The position ID of this square
     */
    public ChanceSquare(int positionID) {
        super(SquareType.CHANCE, positionID, "Chance");
        randomNo = new Random();
    }

    /**
     * Genegertor 4 random money amount from the range of Configs.maximumChanceMoney and Configs.minimumChanceMoney
     * for user to selection, the money amount can be negative.
     */
    @Override
    public  int[] luckyDraw(){
        int[] returnNums = new int[4];
        for (var i = 0; i < 4; i++)
        {
            returnNums[i] = (randomNo.nextInt((Configs.maximumChanceMoney
                                               - Configs.minimumChanceMoney) / 10 + 1) * 10) + Configs.minimumChanceMoney;
        }
        return returnNums;
    }

}
