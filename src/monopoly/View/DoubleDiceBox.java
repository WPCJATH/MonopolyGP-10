package monopoly.View;

import monopoly.Controller.GlobalController;

import java.util.concurrent.TimeUnit;

public class DoubleDiceBox extends Widget{
    private final Dice dice1;
    private final Dice dice2;
    private final int diceNumber1;
    private final int diceNumber2;

    public DoubleDiceBox(int id, String name, int diceNumber1, int diceNumber2, boolean isMandatory){
        super(-1,-1,26,12);
        setContent(GlobalController.preLoadModels.messageBox);

        this.diceNumber1 = diceNumber1;
        this.diceNumber2 = diceNumber2;

        Label headLabel = new Label(63, 1, 1,
                " ↑ Time Limit            Roll two Dice                         ");

        Label callNameLabel = new Label(25, 1, 2, "   To Player " + id + ' ' + name + ':');
        callNameLabel.setLayout("left");
        Label messageLabel = new Label(63, 1, 3, "Press (push Enter) the Roll button to row your dice.");
        dice1 = new Dice(16, 6);
        dice2 = new Dice(39, 6);
        Button rollButton = new Button(21, 22, 13, "Roll");
        rollButton.setSelected();

        addChildComponent(headLabel);
        addChildComponent(callNameLabel);
        addChildComponent(messageLabel);
        addChildComponent(dice1);
        addChildComponent(dice2);
        addChildComponent(rollButton);

        if (isMandatory){
            Label label1 = new Label(63, 1, 4,
                    "You have to row the dice because you do not have enough money.");
            addChildComponent(label1);
        }
    }

    public int listenOnSelection(){
        while(true){
            switch (GlobalController.keyboardListener.listenCharInput()) {
                case 10, 8 -> {
                    return 1;
                }
                default -> {}
            }
        }
    }

    public void rollDice(){
        dice1.animation(diceNumber1);
        dice2.animation(diceNumber2);
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ignored) {}
    }
}
