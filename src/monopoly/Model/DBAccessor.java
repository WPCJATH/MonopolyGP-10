package monopoly.Model;

import monopoly.Controller.GameController;
import monopoly.Controller.GlobalController;
import monopoly.View.PreLoadModels;
import monopoly.View.Window;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAccessor {
    static final String DB_FILE_PATH = "db_data.json";
    GameController gameController;

    public DBAccessor(GameController gameController) {
        this.gameController = gameController;
    }

    public static HistoryGame[] getHistoryGameList() {
        return new HistoryGame[1];
    }

    public static HistoryGame LoadGame(int gameId) {
        return new HistoryGame(0, "", new Player[1], "");
    }

    public static void SaveGame(HistoryGame game) {
    }

    /**
     * Use BufferedWriter class to store all necessary data of current game to the file at DB_FILE_PATH.
     */
    public void saveAllData() {
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
            out.write(Integer.toString(this.gameController.round));
            out.newLine();

            // game which-player's-ture
            out.write(Integer.toString(this.gameController.whosTurn));
            out.newLine();

            // number of players
            int num_of_players = this.gameController.players.length;
            out.write(Integer.toString(num_of_players));
            out.newLine();
            for (int i = 0; i < num_of_players; ++i) {
                Player player = this.gameController.players[i];

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

                // write player position ID
                out.write(Integer.toString(player.getPositionID()));
                out.newLine();

                // write player property IDs
                ArrayList<Integer> propertyIds = (ArrayList<Integer>) player.getPropertyIds().clone();
                int num_of_propertyID = propertyIds.size();
                out.write(num_of_propertyID); // write number of property IDs
                out.newLine();
                for (int j = 0; j < num_of_propertyID; ++j) {
                    // write IDs
                    out.write(Integer.toString(propertyIds.get(j)));
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
     */
    public GameController loadAllData() {
        BufferedReader reader = null;
        List<Player> players = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(DB_FILE_PATH);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);

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
                for (int j=0;j<num_of_propertyIds;++j) {
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
            GameController gameController = new GameController((Player[]) players.toArray());
            gameController.round = round;
            gameController.whosTurn = whosTurn;

            reader.close();
            return gameController;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        PreLoadModels preLoadModels = new PreLoadModels();
        GlobalController.preLoadModels = preLoadModels;
        Configs configs = new Configs();
        GlobalController.config = configs;
        Window window = new Window();
        GlobalController.window = window;
        DBAccessor dbAccessor = new DBAccessor(new GameController(new Player[]{
                new Player(100, 1, "p1"),
                new Player(9999, 2, "p2")
        }));
        System.out.println("dbAccessor created successfully.");
        dbAccessor.saveAllData();
        GameController gameController = dbAccessor.loadAllData();
    }
}
