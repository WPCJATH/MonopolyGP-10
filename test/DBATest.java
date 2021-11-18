import monopoly.Controller.GameController;
import monopoly.Controller.GlobalController;
import monopoly.Model.*;
import monopoly.View.PreLoadModels;
import monopoly.View.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

// The Test of Class DBAccessor, a class can load/save data to the database
public class DBATest {

    @BeforeAll
    // Create the needed data structures to make gameSave work
    static void before() {
        GlobalController.preLoadModels = new PreLoadModels();
        GlobalController.config = new Configs();
        GlobalController.window = new Window();
    }


    static Player[] players1 = {
            new Player(100, 2,
                    "A", 0, false, "FREE"),
            new Player(400, 2,
                    "B", 0, false, "INJAILROUND1"),
            new Player(1000, 2,
                    "C", 0, false, "FREE"),
            new Player(-200, 2,
                    "D", 0, true, "FREE"),
    };

    static Player[] players2 = {
            new Player(100, 2,
                    "A", 0, false, "FREE"),
            new Player(400, 2,
                    "B", 0, false, "INJAILROUND1"),
            new Player(1000, 2,
                    "C", 0, false, "FREE"),
            new Player(-200, 2,
                    "D", 0, true, "FREE"),
            new Player(1000, 2,
                    "C", 0, false, "FREE"),
            new Player(-200, 2,
                    "D", 0, true, "FREE"),
    };

    // save of load the game, if the saved game and loaded game is 1 to 1 match, then the DBAccessor cannot have problems.
    @Test
    public void TestAll() {
        // delete existing saved game data
        try {
            File file = new File(DBAccessor.DB_FILE_PATH);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " deleted successfully.");
                } else {
                    System.out.println("Delete file failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test 1
        // create a game
        GlobalController.gameController = new GameController(players1);
        // write down current static game values
        int round = GameController.round;
        int whosTurn = GameController.whosTurn;
        Player[] players = GameController.players.clone();
        // save game
        DBAccessor.SaveGame();
        // load saved game
        GlobalController.gameController = DBAccessor.LoadGame();
        // run test
        assert GameController.round==round;
        assert GameController.whosTurn==whosTurn;
        assert players.length == GameController.players.length;
        for (int i=0;i<players.length;++i){
            assert players[i].equals(GameController.players[i]);
        }

        // test 2
        // create a game
        GlobalController.gameController = new GameController(players2);
        // modify game states
        GameController.whosTurn = 3;
        GameController.round = 99;
        // write down current static game values
        round = GameController.round;
        whosTurn = GameController.whosTurn;
        players = GameController.players.clone();
        // save game
        DBAccessor.SaveGame();
        // load saved game
        GlobalController.gameController = DBAccessor.LoadGame();
        // run test
        assert GameController.round==round;
        assert GameController.whosTurn==whosTurn;
        assert players.length == GameController.players.length;
        for (int i=0;i<players.length;++i){
            assert players[i].equals(GameController.players[i]);
        }

    }
}
