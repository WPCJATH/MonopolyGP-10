package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Dice extends Widget{
    private final int playTime;

    public Dice(int x, int y){
        super(-1, -1, x, y);
        setContent(GlobalController.preLoadModels.diceUnknown);
        playTime = Configs.diceAnimationDisplayTime;
    }

    public void animation(int number){
        long currentTime = new Date().getTime();
        while ((new Date().getTime() - currentTime)/1000 < playTime){
            int n = (int)(Math.random()*100)%Configs.DiceSideNumber + 1;
            setDiceNumber(n);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ignored) {}
        }
        setDiceNumber(number);
    }

    public void setDiceNumber(int number){
        switch (number) {
            case 1 -> setContent(GlobalController.preLoadModels.diceNumber1);
            case 2 -> setContent(GlobalController.preLoadModels.diceNumber2);
            case 3 -> setContent(GlobalController.preLoadModels.diceNumber3);
            case 4 -> setContent(GlobalController.preLoadModels.diceNumber4);
            case 5 -> setContent(GlobalController.preLoadModels.diceNumber5);
            case 6 -> setContent(GlobalController.preLoadModels.diceNumber6);
        }
        //GlobalController.currentWindow.update();
    }


}
