package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;

import java.util.concurrent.TimeUnit;

public class InJailAskBox extends Widget{
    private final Button rollDiceButton;
    private final Button postBailButton;
    private int currentSelectionIndex;

    public InJailAskBox(int id, String name){
        super(-1,-1,26,12);
        setContent(GlobalController.preLoadModels.messageBox);

        Label headLabel = new Label(63, 1, 1,
                " ↑ Time Limit               In Jail                            ");

        Label callNameLabel = new Label(27, 1, 2, "To Player " + id + ' ' + name+ ':');
        Label messageLabel = new Label(63, 1, 3, "Do you want to Rall Dice or Post Bail?");

        Widget jail = new Widget(-1, -1, 14, 6);
        jail.setContent(GlobalController.preLoadModels.jail);

        Label bailLabel = new Label(14, 31, 8, "Bail:      HKD");
        Label bailMoneyLabel = new Label(4, 37, 8, String.valueOf(Configs.BailFee));

        rollDiceButton = new Button(21, 8, 13, "Roll Dice");
        postBailButton = new Button(21, 36, 13, "Post Bail");

        addChildComponent(headLabel);
        addChildComponent(callNameLabel);
        addChildComponent(messageLabel);
        addChildComponent(jail);
        addChildComponent(bailLabel);
        addChildComponent(bailMoneyLabel);
        addChildComponent(rollDiceButton);
        addChildComponent(postBailButton);

        rollDiceButton.setSelected();
        currentSelectionIndex = 0;
    }


    @Override
    public int listenOnSelection(){
        while(true){
            switch (GlobalController.keyboardListener.listenCharInput()){
                case 'A':
                case 'a':
                case 'D':
                case 'd':
                    go();
                    break;
                case 10:
                    return currentSelectionIndex;
                case 8, 127:
                    return -1;
            }
        }
    }

    public void OnSelection(int selection){
        if (selection!=0 && selection!=1)
            throw new IllegalArgumentException();
        while (selection!=currentSelectionIndex){
            go();
            try {TimeUnit.MILLISECONDS.sleep(1000);} catch (InterruptedException ignored) {}
        }
    }

    private void go(){
        if (currentSelectionIndex==0){
            rollDiceButton.setUnselected();
            postBailButton.setSelected();
            currentSelectionIndex = 1;
        }
        else {
            rollDiceButton.setSelected();
            postBailButton.setUnselected();
            currentSelectionIndex = 0;
        }
    }
}
