package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;

import java.util.concurrent.TimeUnit;

public class MessageBar extends Widget{
    private String message;
    private final Label messageLabel;
    private boolean isOccupied;

    public MessageBar(){
        super(-1, -1, 49, 24);
        setContent(GlobalController.preLoadModels.messageBar);

        message = "";
        messageLabel = new Label(16, 1, 2,message);
        addChildComponent(messageLabel);

        isOccupied = false;
    }

    private void waitForFree(){
        while (isOccupied){try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}}
    }

    private void setMessage(){
        waitForFree();
        isOccupied = true;
        messageLabel.setText(message);
        sleep();
        isOccupied = false;
    }

    public void roundBegins(int round){
        message = String.format("Round %d", round);
        setMessage();
    }

    public void playerTurn(int id){
        message = String.format("Player %d's Turn", id);
        setMessage();
    }

    public void dealIsDone(){
        message = "Deal is Done!";
        setMessage();
    }

    public void goToJail(int id){
        message = String.format("Player %d Go Jail", id);
        setMessage();
    }

    public void broke(int id){
        message = String.format("Player %d broke", id);
        setMessage();
    }

    public void released(){
        message = "Released!";
        setMessage();
    }

    public void failed(){
        message = "Oops, failed!";
        setMessage();
    }

    private static void sleep(){
        try {TimeUnit.SECONDS.sleep(Configs.messageOccupyTime);} catch (InterruptedException ignored) {}
    }
}
