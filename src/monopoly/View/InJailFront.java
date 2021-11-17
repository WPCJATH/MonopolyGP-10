package monopoly.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InJailFront extends Widget{
    private final Label playerLabel;
    private final List<String> players;
    private String playerLabelText;

    public InJailFront(int x, int y){
        super(12, 1, x, y);
        initialContent();

        players = new ArrayList<>();
        playerLabelText = "In Jail";
        playerLabel = new Label(12, 0, 0, playerLabelText);
        playerLabel.setLayout("right");
        addChildComponent(playerLabel);
    }

    public void addPlayer(int id) {
        players.add(String.valueOf(id));
        Collections.sort(players);
        playerLabelText = playerLabelTextMaker();
        playerLabel.setText(playerLabelText);
    }

    public void removePlayer(int id){
        players.remove(String.valueOf(id));
        if (players.size()==0)
            playerLabelText = "In Jail";
        else
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

}
