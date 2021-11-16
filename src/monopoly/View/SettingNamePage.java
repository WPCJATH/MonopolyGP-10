package monopoly.View;

import monopoly.Controller.GlobalController;

public class SettingNamePage extends Widget{
    private final InputField inputField;
    Label label1;

    private static final String[] ordinalNumberTable = {"1st", "2nd", "3rd", "4th", "5th", "6th"};
    private int playerNumber;
    private int pageIndex;
    private String[] PlayerNames;

    private final Button[] buttons;
    private int currentSelection;

    public SettingNamePage(int x, int y) {
        super(-1, -1, x,y);
        setContent(GlobalController.preLoadModels.menu);

        Button button1 = new Button(18, 14, 10, "Next");
        Button button2 = new Button(18, 14, 13, "Back");

        label1 = new Label(33, 3, 2);
        Label label2 = new Label(45, 1, 3, "(No more than 10 characters)");
        inputField = new InputField(31, 8, 5);

        buttons = new Button[]{inputField, button1, button2};
        inputField.setSelected();
        currentSelection = 0;

        playerNumber = -1;
        pageIndex = -1;

        addChildComponent(label1);
        addChildComponent(label2);
        addChildComponent(inputField);
        addChildComponent(button1);
        addChildComponent(button2);
    }

    public void setPlayerNumber(int num){
        playerNumber = num;
        pageIndex = 0;
        PlayerNames = new String[playerNumber];
        label1.setText(LabelTextMaker(pageIndex));
    }

    public String[] getPlayerNames(){
        return PlayerNames;
    }

    public void clear(){
        playerNumber = -1;
        pageIndex = -1;
        PlayerNames = null;
    }

    @Override
    public int listenOnSelection(){
        if (playerNumber==-1)
            throw new IllegalCallerException("Set the player number first.");
        while (true){
            char ch = GlobalController.keyboardListener.listenCharInput();
            if (currentSelection==0) {
                switch (ch) {
                    case 10 -> goNext();
                    case 45 -> inputField.backSpace();
                    default -> inputField.fieldAppendText(ch);
                }
                continue;
            }
            switch (ch) {
                case 'w', 'W' -> goPrevious();
                case 's', 'S' -> goNext();
                case 10 -> {
                    if (currentSelection == 1){ // next
                        PlayerNames[pageIndex] = inputField.getText();
                        if (pageIndex==playerNumber-1)
                            return 1;
                        else
                            goNextPage();
                    }
                    if (currentSelection == 2) // back
                        if (pageIndex==0)
                            return 0;
                        else
                            goPreviousPage();
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

    private void goNextPage(){
        pageIndex++;
        label1.setText(LabelTextMaker(pageIndex));
        inputField.setText("");
        buttons[1].setUnselected();
        inputField.setSelected();
        currentSelection = 0;
    }

    private void goPreviousPage(){
        pageIndex--;
        inputField.setText(PlayerNames[pageIndex]);
        label1.setText(LabelTextMaker(pageIndex));
        buttons[2].setUnselected();
        inputField.setSelected();
        currentSelection = 0;
    }

    private static String LabelTextMaker(int index){
        String seq1 = "Enter the Name of the ";
        String seq2 = " Player:";
        return seq1 + ordinalNumberTable[index] + seq2;
    }

}
