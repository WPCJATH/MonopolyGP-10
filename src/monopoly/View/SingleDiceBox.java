package monopoly.View;

import monopoly.Controller.GlobalController;

import java.util.concurrent.TimeUnit;

public class SingleDiceBox extends Widget{
    private final Dice dice;
    private final int diceNumber;

    public SingleDiceBox(int id, String name, int diceNumber){
        super(-1,-1,26,12);
        setContent(GlobalController.preLoadModels.messageBox);

        this.diceNumber = diceNumber;

        Label headLabel = new Label(63, 1, 1,
                " ↑ Time Limit            Roll a Dice                           ");

        Label callNameLabel = new Label(25, 1, 2, "  To Player " + id + ' ' + name + ":");
        callNameLabel.setLayout("left");
        Label messageLabel = new Label(63, 1, 3, "Press (push Enter) the Roll button to row your dice.");
        dice = new Dice(27, 6);
        Button rollButton = new Button(21, 22, 13, "Roll");
        rollButton.setSelected();

        addChildComponent(headLabel);
        addChildComponent(callNameLabel);
        addChildComponent(messageLabel);
        addChildComponent(dice);
        addChildComponent(rollButton);
    }

    public int listenOnSelection(){
        while(true){
            switch (GlobalController.keyboardListener.listenCharInput()){
                case 10, 8 -> {
                    return 1;
                }
                default -> {}
            }
        }
    }

    public void rollDice(){
        dice.animation(diceNumber);
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ignored) {}
    }
}
