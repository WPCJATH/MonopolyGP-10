package monopoly.Model;

import java.sql.*;
import java.util.ArrayList;

public class DBAccessor {
    public static HistoryGame[] getHistoryGameList(){
        return new HistoryGame[1];
    }

    public static HistoryGame LoadGame(int gameId) {
        return new HistoryGame(0,"", new Player[1], "");
    }

    public static void SaveGame(HistoryGame game){
    }

}

