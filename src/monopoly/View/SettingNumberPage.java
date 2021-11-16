package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;

public class SettingNumberPage extends Widget{
    public int humanPlayerNumber;
    public int robotPlayerNumber;
    public int robotLevel;

    private final String[] baseStringLevel = {" -    low    + ", " -    mid    + ", " -    high   + "};

    private final Button[] buttons;
    private int currentSelection;

    public SettingNumberPage(int x, int y) {
        super(-1, -1, x,y);
        setContent(GlobalController.preLoadModels.menu);
        Label label1 = new Label(24, 1, 2, "No. of Players");
        Label label2 = new Label(24, 1, 5, "No. of Robot Players");
        Label label3 = new Label(24, 1, 8, "Robot Level");

        humanPlayerNumber = 1;
        robotPlayerNumber = 0;
        robotLevel = 0;

        Button button1 = new Button(19, 25, 1, PlayerNumberTransfer(humanPlayerNumber));
        Button button2 = new Button(19, 25, 4, PlayerNumberTransfer(robotPlayerNumber));
        Button button3 = new Button(19, 25, 7, baseStringLevel[robotLevel]);
        Button button4 = new Button(18, 14, 10, "Next");
        Button button5 = new Button(18, 14, 13, "Back");

        buttons = new Button[]{button1, button2, button3, button4, button5};
        buttons[0].setSelected();
        currentSelection = 0;

        addChildComponent(label1);
        addChildComponent(label2);
        addChildComponent(label3);
        addChildComponent(button1);
        addChildComponent(button2);
        addChildComponent(button3);
        addChildComponent(button4);
        addChildComponent(button5);
    }

    @Override
    public int listenOnSelection(){
        while (true){
            char ch = GlobalController.keyboardListener.listenCharInput();
            switch (ch) {
                case 'w', 'W' -> goPrevious();
                case 's', 'S' -> goNext();
                case 'A', 'a' -> goLeft();
                case 'D', 'd' -> goRight();
                case 10 -> {
                    if (currentSelection == 3) // next
                        return 1;
                    if (currentSelection == 4) // back
                        return 0;
                }
                default -> {}
            }
        }
    }

    public void resetSelection(){
        buttons[currentSelection].setUnselected();
        currentSelection = 0;
        buttons[currentSelection].setSelected();
    }

    private void goNext(){
        buttons[currentSelection].setUnselected();
        if (currentSelection < buttons.length - 1) currentSelection++;
        else currentSelection = 0;
        buttons[currentSelection].setSelected();
    }

    private void goPrevious(){
        buttons[currentSelection].setUnselected();
        if (currentSelection != 0) currentSelection--;
        else currentSelection = buttons.length - 1;
        buttons[currentSelection].setSelected();
    }

    private void goRight(){
        switch (currentSelection){
            case 0:
                if (robotPlayerNumber + humanPlayerNumber < Configs.maximumPlayerNumber){
                    humanPlayerNumber++;
                    buttons[0].setText(PlayerNumberTransfer(humanPlayerNumber));
                }
                break;
            case 1:
                if (robotPlayerNumber + humanPlayerNumber < Configs.maximumPlayerNumber){
                    robotPlayerNumber++;
                    buttons[1].setText(PlayerNumberTransfer(robotPlayerNumber));
                }
                break;
            case 2:
                if (robotLevel < 2){
                    robotLevel++;
                    buttons[2].setText(baseStringLevel[robotLevel]);
                }
            default:
                break;
        }
    }

    private void goLeft(){
        switch (currentSelection){
            case 0:
                if (humanPlayerNumber > 1){
                    humanPlayerNumber--;
                    buttons[0].setText(PlayerNumberTransfer(humanPlayerNumber));
                }
                break;
            case 1:
                if (robotPlayerNumber > 0){
                    robotPlayerNumber--;
                    buttons[1].setText(PlayerNumberTransfer(robotPlayerNumber));
                }
                break;
            case 2:
                if (robotLevel > 0){
                    robotLevel--;
                    buttons[2].setText(baseStringLevel[robotLevel]);
                }
            default:
                break;
        }
    }

    public int getPlayerNumber(){
        return humanPlayerNumber + robotPlayerNumber;
    }

    public int getRobotLevel(){
        return robotLevel;
    }


    private static String PlayerNumberTransfer(int num){
        String baseStringPlayerLeft = " -     ";
        String baseStringPlayerRight = "     + ";
        return baseStringPlayerLeft + num + baseStringPlayerRight;
    }
}

