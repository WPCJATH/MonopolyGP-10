package monopoly.View;

import monopoly.Model.SquareBackend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SquareFront extends Widget{
    private Label hostId;
    private final Label playerLabel;
    private final List<String> players;
    private SquareBackend squareBackend;
    private String playerLabelText;

    public SquareFront(int x, int y, SquareBackend squareBackend) {
        super(16, 4, x, y);
        initialContent();

        this.squareBackend = squareBackend;
        Label idLabel = new Label(2, 1, 0, addZeroFront(squareBackend.getPositionID()));
        Label nameLabel = new Label(11, 4, 0, squareBackend.getName());
        Label moneyLabel = new Label(16, 0, 1, squareBackend.getPrice() + " HKD");
        Label label1 = new Label(14, 1, 2, "Host       HKD");
        hostId = new Label(1,6, 2, "-");
        Label rentLabel = new Label(3, 8, 2, String.valueOf(squareBackend.getRent()));
        playerLabel = new Label(14, 2, 3);
        playerLabel.setLayout("right");

        players = new ArrayList<>();
        playerLabelText = "";
        addChildComponent(idLabel);
        addChildComponent(nameLabel);
        addChildComponent(moneyLabel);
        addChildComponent(label1);
        addChildComponent(hostId);
        addChildComponent(rentLabel);
        addChildComponent(playerLabel);
    }

    public void clearHost(){
        if (!squareBackend.hasHost())
            throw new IllegalCallerException("The property has no host.");
        hostId.setText("-");
    }

    public SquareFront(int x, int y){
        super(14, 1, x+2, y+3);
        initialContent();

        players = new ArrayList<>();
        playerLabelText = "";
        playerLabel = new Label(14, 0, 0);
        playerLabel.setLayout("right");
        addChildComponent(playerLabel);
    }

    public void setHost(int id){
        if (!squareBackend.hasHost())
            throw new IllegalCallerException("The property has no host.");
        hostId.setText(String.valueOf(id));
    }

    public void passBy(int id){
        playerLabel.setText(String.valueOf(id) + ' ' + playerLabelText);
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException ignored) {}
        playerLabel.setText(playerLabelText);
    }

    public void addPlayer(int id) {
        players.add(String.valueOf(id));
        Collections.sort(players);
        playerLabelText = playerLabelTextMaker();
        playerLabel.setText(playerLabelText);
    }

    public void removePlayer(int id){
        players.remove(String.valueOf(id));
        playerLabelText = playerLabelTextMaker();
        playerLabel.setText(playerLabelText);
    }

    private String playerLabelTextMaker(){
        StringBuilder sb = new StringBuilder();
        for (String id:players){
            sb.append(id).append(' ');
        }
        return sb.toString();
    }

    private static String addZeroFront(int num){
        String numStr = String.valueOf(num);
        if (numStr.length()==1)
            return '0' + numStr;
        return numStr;
    }
}
