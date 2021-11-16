package monopoly.Model;

import java.util.Random;

public class ChanceSquare extends SquareBackend {
    private final Random randomNo;
    public ChanceSquare(int positionID) {
        super(SquareType.CHANCE, positionID, "Chance");
        randomNo = new Random();
    }

    public  int[] luckyDraw(){
        int[] returnNums = new int[4];
        for (int i=0; i<4; i++)
        {
            returnNums[i] = randomNo.nextInt((Configs.maximumChanceMoney
                    - Configs.minimumChanceMoney)/10 + 1)*10 + Configs.minimumChanceMoney;
        }
        return returnNums;
    }

}
