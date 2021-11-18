package monopoly.Model;

import monopoly.Controller.GameController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAccessor {
    static final String DB_FILE_PATH = "gameBackup/data.json";

    /**
     * Use BufferedWriter class to store all necessary data of current game to the file at DB_FILE_PATH.
     */
    public static void SaveGame() {
        BufferedWriter out = null;
        File file = new File(DB_FILE_PATH);
        // create one if not exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // write data into file
        try {
            // default buffer size: {private static int defaultCharBufferSize = 8192;}
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));

            // game round
            out.write(Integer.toString(GameController.round));
            out.newLine();

            // game which-player's-ture
            out.write(Integer.toString(GameController.whosTurn));
            out.newLine();

            // number of players
            int num_of_players = GameController.players.length;
            out.write(Integer.toString(num_of_players));
            out.newLine();
            for (int i = 0; i < num_of_players; ++i) {
                Player player = GameController.players[i];

                // write player money
                out.write(Integer.toString(player.getMoney()));
                out.newLine();

                // write player ID
                out.write(Integer.toString(player.getPlayerID()));
                out.newLine();

                // write player name
                out.write(player.getNameString());
                out.newLine();

                // write player bankrupt state
                out.write(Boolean.toString(player.isBankrupt())); // boolean
                out.newLine();

                // write player inPrisonState
                out.write(player.inPrisonState.name()); // InPrisonState Enum
                out.newLine();

                // write player position ID
                out.write(Integer.toString(player.getPositionID()));
                out.newLine();

                // write player property IDs
                ArrayList<Integer> propertyIds = (ArrayList<Integer>) player.getPropertyIds().clone();
                out.write(Integer.toString(propertyIds.size())); // write number of property IDs
                out.newLine();
                for (int id : propertyIds) {
                    // write IDs
                    out.write(Integer.toString(id));
                    out.newLine();
                }

                // write Robot Level
                out.write(Integer.toString(player.getRobotLevel()));
                out.newLine();

                // write isRobot
                out.write(Boolean.toString(player.isRobot()));// boolean
                out.newLine();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // debug info
        System.out.println("Data stored successfully!");
    }

    /**
     * Use BufferedReader class to read all necessary data of the game from the file at DB_FILE_PATH, and load to
     * current game.
     * @Return Return a completed GameController; return null if any exception happens during LoadGame.
     */
    public static GameController LoadGame() {
        BufferedReader reader = null;
        GameController gameController = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(DB_FILE_PATH);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);

            List<Player> players = new ArrayList<>();
            // [game round], [game which-player's-ture], [number of players]
            int round = Integer.parseInt(reader.readLine());
            int whosTurn = Integer.parseInt(reader.readLine());
            int num_of_players = Integer.parseInt(reader.readLine());

            // add all players
            // Attributes list: [player money], [player ID], [player name], [player bankrupt state],
            //                  [player inPrisonState], [player position ID], [player property IDs],
            //                  [Robot Level], [isRobot]
            for (int i = 0; i < num_of_players; ++i) {
                int money = Integer.parseInt(reader.readLine());
                int playerID = Integer.parseInt(reader.readLine());
                String name = reader.readLine();
                boolean isBankrupt = Boolean.parseBoolean(reader.readLine());
                String inPrisonState = reader.readLine();
                int positionID = Integer.parseInt(reader.readLine());

                // read property IDs
                int num_of_propertyIds = Integer.parseInt(reader.readLine());
                ArrayList<Integer> propertyIds = new ArrayList<>();
                for (int j = 0; j < num_of_propertyIds; ++j) {
                    propertyIds.add(Integer.parseInt(reader.readLine()));
                }

                int robot_level = Integer.parseInt(reader.readLine());
                boolean isRobot = Boolean.parseBoolean(reader.readLine());

                Player player = new Player(money, playerID, name, positionID, isBankrupt, inPrisonState);
                if (isRobot) {
                    player.setRobot();
                    player.setRobotLevel(robot_level);
                }
                player.setPropertyIds(propertyIds);
                players.add(player);
            }

            // construct a new gameController using the data above
            Player[] player_list = new Player[players.size()];
            for (int j = 0; j < players.size(); ++j)
                player_list[j] = players.get(j);
            gameController = new GameController(player_list);
            GameController.round = round;
            GameController.whosTurn = whosTurn;

            reader.close();
        } catch (Exception e) {
            gameController = null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gameController;
    }
}
