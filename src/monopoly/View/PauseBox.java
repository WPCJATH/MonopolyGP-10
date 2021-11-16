package monopoly.View;

import monopoly.Controller.GlobalController;

import java.util.concurrent.TimeUnit;

public class PauseBox extends Widget implements Runnable{
    private final Label headLabel;

    private final Button[] buttons;
    private int currenSelectIndex;

    private boolean isContinue;

    public PauseBox() {
        super(-1, -1, 26, 12);
        setContent(GlobalController.preLoadModels.messageBox);

        headLabel = new Label(63, 1, 1, "Paused");
        Button continueButton = new Button(21, 22, 5, "Continue");
        Button saveQuitButton = new Button(21, 22, 8, "Save and Quit");
        Button quitButton = new Button(21, 22, 11, "Quit");

        addChildComponent(headLabel);
        addChildComponent(continueButton);
        addChildComponent(saveQuitButton);
        addChildComponent(quitButton);

        buttons = new Button[]{continueButton, saveQuitButton, quitButton};
        currenSelectIndex = 0;
        buttons[0].setSelected();

        isContinue = true;
    }

    public int listenOnSelection(){
        while (true){
            switch (GlobalController.keyboardListener.listenCharInputOnPause()){
                case 'w':
                case 'W':
                    goPrevious();
                    break;
                case 'S':
                case 's':
                    goNext();
                    break;
                case 10:
                    return currenSelectIndex;
                case 'p':
                case 'P':
                    return 0;
                default:
                    break;
            }
        }
    }

    private void goPrevious(){
        buttons[currenSelectIndex].setUnselected();
        if (currenSelectIndex < buttons.length-1)
            currenSelectIndex++;
        else
            currenSelectIndex = 0;
        buttons[currenSelectIndex].setSelected();
    }

    private void goNext(){
        buttons[currenSelectIndex].setUnselected();
        if (currenSelectIndex > 0)
            currenSelectIndex--;
        else
            currenSelectIndex = buttons.length-1;
        buttons[currenSelectIndex].setSelected();
    }

    public void setStop(){
        isContinue = false;
    }

    @Override
    public void run() {
        while (isContinue){
            headLabel.setText("");
            try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException ignored) {}
            headLabel.setText("pause");
        }
    }
}
