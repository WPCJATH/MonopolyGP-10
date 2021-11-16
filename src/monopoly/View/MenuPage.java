package monopoly.View;

import monopoly.Controller.GlobalController;

public class MenuPage extends Widget{
    private final Button[] buttons;
    private int currentSelection;

    public MenuPage(int x,int y){
        super(-1, -1,x,y);
        setContent(GlobalController.preLoadModels.menu.clone());

        Button newGameButton = new Button(16, 16,3, "New Game");
        Button continueButton = new Button(16, 16, 6, "Continue");
        Button userManualButton = new Button(16, 16, 9, "User Manual");
        Button quitButton = new Button(16, 16, 12, "Quit");
        buttons = new Button[]{newGameButton, continueButton, userManualButton, quitButton};

        for(Button button: buttons)
            addChildComponent(button);
        buttons[0].setSelected();
        currentSelection = 0;
    }

    @Override
    public int listenOnSelection(){
        while (true){
            char ch = GlobalController.keyboardListener.listenCharInput();
            switch (ch){
                case 'w':
                case 'W':
                    goPrevious();
                    break;
                case 's':
                case 'S':
                    goNext();
                    break;
                case 10:
                    return currentSelection;
                default:
            }
        }


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

}
