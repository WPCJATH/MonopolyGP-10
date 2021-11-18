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
                    "C", 0, false, "FREE")
    };

    static Player[] players3 = {
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
            if (file.delete()) {
                System.out.println(file.getName() + " deleted successfully.");
            } else {
                System.out.println("Delete file failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalController.gameController = new GameController(players1);
        DBAccessor.SaveGame();
        GameController loaded_gc = DBAccessor.LoadGame();
        assert GlobalController.gameController.equals(loaded_gc);
        // save the games
//        DBAccessor.SaveGame(historyGame1);
//        DBAccessor.SaveGame(historyGame2);
//        DBAccessor.SaveGame(historyGame3);

        // load the history game list. The getHistoryGameList will return a lighted historyGames class to tell the user
        // basic information like stored data, name, player names etc.
//        HistoryGame[] historyGames = DBAccessor.getHistoryGameList();
//        assert historyGames[0].gameListToString().equals(historyGame1.gameListToString());
//        assert historyGames[1].gameListToString().equals(historyGame2.gameListToString());
//        assert historyGames[2].gameListToString().equals(historyGame3.gameListToString());

        // gameListToString and toString will well format all the information including player information and square
        // infomation, so that only if these 2 are "equal", the test will past.

        // load and compare the history games.
//        assert DBAccessor.LoadGame(1).toString().equals(historyGame1.toString());
//        assert DBAccessor.LoadGame(2).toString().equals(historyGame2.toString());
//        assert DBAccessor.LoadGame(3).toString().equals(historyGame3.toString());
    }
}
