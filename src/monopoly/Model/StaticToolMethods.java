package monopoly.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StaticToolMethods {

    public static String randomNameGenerator(){
        return "Xavier";
    }

    public static Player[] playersGenerator(String[] names, int playerNumbers, int robotLevel){
        Player[] players = new Player[playerNumbers];
        int i = 0;

        ArrayList<String> playerNames = new ArrayList<>(List.of(names));
        HashMap<String, Boolean> isHumanMap = new HashMap<>();
        for (String name: names){
            isHumanMap.put(name, true);
        }

        while (playerNames.size() < playerNumbers){
            String name = randomNameGenerator();
            playerNames.add(name);
            isHumanMap.put(name, false);
        }

        Collections.shuffle(playerNames);
        for (String name: playerNames){
            players[i] = new Player(0, i+1, name);
            if (!isHumanMap.get(name)){
                players[i].setRobot();
                players[i].setRobotLevel(robotLevel);
            }
            i++;
        }

        return players;
    }

}
