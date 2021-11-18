package monopoly.Model;

import java.util.*;

/**
 * Utility methods class.
 */
public class StaticToolMethods {
    static String[] names = new String[]{"James", "Robert", "John", "Michael", "William", "David",
            "Richard", "Joseph", "Thomas", "Charles", "Christopher", "Daniel", "Matthew", "Anthony",
            "Mark", "Donald", "Steven", "Paul", "Andrew", "Joshua", "Kenneth", "Kevin", "Brian", "George",
            "Edward", "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan", "Jacob", "Gary", "Nicholas", "Eric",
            "Jonathan", "Stephen", "Larry", "Justin", "Scott", "Brandon", "Benjamin", "Samuel", "Gregory",
            "Frank", "Alexander", "Raymond", "Patrick", "Jack", "Dennis", "Jerry", "Tyler", "Aaron", "Jose",
            "Adam", "Henry", "Nathan", "Douglas", "Zachary", "Peter", "Kyle", "Walter", "Ethan", "Jeremy",
            "Harold", "Keith", "Christian", "Roger", "Noah", "Gerald", "Carl", "Terry", "Sean", "Austin", "Arthur",
            "Lawrence", "Jesse", "Dylan", "Bryan", "Joe", "Jordan", "Billy", "Bruce", "Albert", "Willie", "Gabriel",
            "Logan", "Alan", "Juan", "Wayne", "Roy", "Ralph", "Randy", "Eugene", "Vincent", "Russell", "Elijah",
            "Louis", "Bobby", "Philip", "John"};

    /**
     * @return a random player name.
     */
    public static String randomNameGenerator(){
        Random random = new Random();
        String name = names[random.nextInt(names.length-1)] + "-bot";
        if (name.length() >= 12)
            return randomNameGenerator();
        return name;
    }

    /**
     * Generate bot players.
     * @param names name list of the bot players.
     * @param playerNumbers number of players to be generated.
     * @param robotLevel difficulty levels of robots.
     * @return
     */
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
