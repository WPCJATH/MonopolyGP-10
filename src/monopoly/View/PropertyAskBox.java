package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.SquareBackend;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PropertyAskBox extends Widget{
    private final Button yesButton;
    private final Button noButton;
    private int currentSelectionIndex;

    public PropertyAskBox(SquareBackend property, int id, String name){
        super(-1,-1,26,12);
        setContent(GlobalController.preLoadModels.messageBox);

        Label headLabel = new Label(63, 1, 1,
                " ↑ Time Limit          Buy a Property                          ");

        Label callNameLabel = new Label(27, 1, 2, "  To Player " + id + ' ' + name+':');
        callNameLabel.setLayout("left");
        Label messageLabel = new Label(63, 1, 3, "Do you want to buy the property shown below?");

        Widget house = new Widget(-1, -1, 16, 6);
        house.setContent(GlobalController.preLoadModels.house);

        Label nameLabel = new Label(5, 31, 7, "Name:");
        Label prizeLabel = new Label(17, 31, 8, "Prize:        HKD");
        Label rentLabel = new Label(17, 31, 9,  "Rent:         HKD");

        Label propertyName = new Label(12, 36, 7, property.getName());
        propertyName.setLayout("right");
        Label prize = new Label(4, 40, 8, String.valueOf(property.getPrice()));
        prize.setLayout("right");
        Label rent = new Label(4, 40, 9, String.valueOf(property.getRent()));
        rent.setLayout("right");

        yesButton = new Button(15, 13, 13, "Yes");
        noButton = new Button(15, 35, 13, "No");

        addChildComponent(headLabel);
        addChildComponent(callNameLabel);
        addChildComponent(messageLabel);
        addChildComponent(house);
        addChildComponent(nameLabel);
        addChildComponent(prizeLabel);
        addChildComponent(rentLabel);
        addChildComponent(propertyName);
        addChildComponent(prize);
        addChildComponent(rent);
        addChildComponent(yesButton);
        addChildComponent(noButton);

        yesButton.setSelected();
        currentSelectionIndex=0;
    }

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
                    if (currentSelectionIndex==0)
                        return 1;
                    else
                        return 0;
                case 8:
                    return -1;
            }
        }
    }

    public void OnSelection(int selection){
        while (selection!=currentSelectionIndex){
            go();
            try {TimeUnit.MILLISECONDS.sleep(1000);} catch (InterruptedException ignored) {}
        }
    }

    private void go(){
        if (currentSelectionIndex==0){
            yesButton.setUnselected();
            noButton.setSelected();
            currentSelectionIndex = 1;
        }
        else {
            yesButton.setSelected();
            noButton.setUnselected();
            currentSelectionIndex = 0;
        }
    }

}
