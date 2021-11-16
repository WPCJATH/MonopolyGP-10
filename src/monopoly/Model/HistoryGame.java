package monopoly.Model;

import java.util.Date;

public class HistoryGame {
    private final int gameId;
    private final String gameName;
    private final Player[] playerList;
    private final Date storeDate;

    private int WhoseTurn;
    private SquareBackend[] squares;
    private int timeCount;

    public HistoryGame(int id, String name, Player[] playerList, String date){
        this.gameId = id;
        this.gameName = name;
        this.playerList = playerList;
        this.storeDate = parseDate(date);
    }

    public HistoryGame(int id, String name, Player[] playerList, String date,
                       int whoseTurn, SquareBackend[] squares, int timeCount){
        this.gameId = id;
        this.gameName = name;
        this.playerList = playerList;
        this.storeDate = parseDate(date);
        this.WhoseTurn = whoseTurn;
        this.squares = squares;
        this.timeCount = timeCount;
    }

    private static Date parseDate(String date){return new Date();}

    public String gameListToString(){return "";}

    public String toString(){return "";}


    public Player[] getPlayerList() {
        return playerList;
    }

    public Date getStoreDate() {
        return storeDate;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public int getWhoseTurn() {
        return WhoseTurn;
    }

    public SquareBackend[] getSquares() {
        return squares;
    }

    public int getTimeCount() {
        return timeCount;
    }
}
