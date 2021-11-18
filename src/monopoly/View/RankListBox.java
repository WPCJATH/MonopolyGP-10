package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class RankListBox extends Widget{

    public RankListBox(Player[] players) {
        super(-1,-1,26,12);
        setContent(GlobalController.preLoadModels.messageBox);
        Label headLabel = new Label(63, 1, 1,
                "Ranking List");

        Label tableHeadLabel = new Label(63, 1, 3,
                "      No.   Id        Name          Money     Property No.     ");

        addChildComponent(headLabel);
        addChildComponent(tableHeadLabel);

        Comparator<Player> cmp = (o1, o2) -> o2.getMoney() - o1.getMoney();
        Arrays.sort(players, cmp);

        int baseY = 4;
        for (int i=0; i<players.length; i++){
            addChildComponent(new Label(51, 8, baseY++, tableContentGenerator(i, players[i])));
        }

        Button goToMenu = new Button(23, 21, 14, "Go to Menu");
        goToMenu.setSelected();
        addChildComponent(goToMenu);
    }

    public int listenOnSelection(){
        GlobalController.keyboardListener.listenCharInputOnPause();
        return 0;
    }

    private static String tableContentGenerator(int no, Player player){
        return (no+1) +
                addBlanks(String.valueOf(player.getPlayerID()), 6) +
                addBlanks(player.getNameString(), 12) +
                addBlanks(String.valueOf(player.getMoney()), 12) + "HKD" +
                addBlanks(String.valueOf(player.getPropertyNumber()), 16);
    }

    private static String addBlanks(String content, int len){
        StringBuilder contentBuilder = new StringBuilder(content);
        while (contentBuilder.length() < len)
            contentBuilder.insert(0, ' ');
        return contentBuilder.toString();
    }
}
