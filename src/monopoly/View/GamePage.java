package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;
import monopoly.Model.Player;
import monopoly.Model.SquareBackend;
import monopoly.Model.SquareType;

public class GamePage extends Widget{
    private final Player[] players;
    private final PlayerBar[] playerBars;
    private final SquareBackend[] squareBackends;
    private final SquareFront[] squareFronts;


    public GamePage(Player[] players, SquareBackend[] squares) {
        super(-1, -1, 0, 0);
        setContent(GlobalController.preLoadModels.gameBoard);

        this.players = players;
        this.squareBackends = squares;

        Label incomeTaxLabel = new Label(2, 51, 33, String.valueOf(Configs.taxRate));
        Label goInitialMoneyLabel = new Label(4, 98, 33, String.valueOf(Configs.initialFunding));
        addChildComponent(incomeTaxLabel);
        addChildComponent(goInitialMoneyLabel);

        assert(squares[0].getType() == SquareType.GO);
        assert(squares[3].getType() == SquareType.INCOMETAX);
        assert(squares[5].getType() == SquareType.INJAILORJUSTVISITING);
        assert(squares[8].getType() == SquareType.CHANCE);
        assert(squares[10].getType() == SquareType.FREEPARKING);
        assert(squares[12].getType() == SquareType.CHANCE);
        assert(squares[16].getType() == SquareType.GOTOJAIL);
        assert(squares[18].getType() == SquareType.CHANCE);

        squareFronts = new SquareFront[20];
        for (int i=0; i<20; i++){
            if (squareBackends[i].getType()==SquareType.PROPERTY)
                squareFronts[i] = new SquareFront(squarePositions[i][0], squarePositions[i][1], squareBackends[i]);
            else
                squareFronts[i] = new SquareFront(squarePositions[i][0], squarePositions[i][1], squareBackends[i], 0);
            addChildComponent(squareFronts[i]);
        }

        playerBars = new PlayerBar[6];
        for (int i=0; i<6; i++){
            if (i < players.length){
                playerBars[i] = new PlayerBar(playerPositions[i][0], playerPositions[i][1], players[i]);
            }
            else{
                playerBars[i] = new PlayerBar(playerPositions[i][0], playerPositions[i][1], i);
            }
            addChildComponent(playerBars[i]);
        }
    }

    public void setPlayerBarSelected(int index){
        playerBars[index].setSelected();
    }

    public void setPlayerBarUnselected(int index){
        playerBars[index].setUnselected();
    }

    public void upDatePlayerBar(int index){
        new Thread(() -> {
            playerBars[index].updateMoney();
            playerBars[index].updateState();
        }).start();
    }

    public void setAllPlayerPositions(){
        for (Player player: players){
            squareFronts[player.getPositionID()-1].addPlayer(player.getPlayerID());
        }
    }

    public void displaySingleDiceBox(int index, int number){
        SingleDiceBox singleDiceBox= new SingleDiceBox(index+1, players[index].getNameString(), number);
        addChildComponent(singleDiceBox);
        singleDiceBox.listenOnSelection();
        removeChildComponent(singleDiceBox);
    }

    public int displayPauseBox(){
        PauseBox pauseBox = new PauseBox();
        addChildComponent(pauseBox);
        Thread thread = new Thread(pauseBox);
        thread.start();

        int returnNum = pauseBox.listenOnSelection();

        pauseBox.setStop();
        try {thread.join();} catch (InterruptedException ignored) {}
        removeChildComponent(pauseBox);
        return returnNum;
    }

    public int displayPropertyAskBox(int playerIndex, int squareIndex){
        PropertyAskBox propertyAskBox = new PropertyAskBox(
                squareBackends[squareIndex], playerIndex + 1, players[playerIndex].getNameString());
        addChildComponent(propertyAskBox);
        int returnNum = propertyAskBox.listenOnSelection();
        removeChildComponent(propertyAskBox);
        return returnNum;
    }

    public int displayLuckyDrawBox(int index, int[] cardNumbers){
        LuckyDrawBox luckyDrawBox = new LuckyDrawBox(index+1, players[index].getNameString(), cardNumbers);
        addChildComponent(luckyDrawBox);
        int reNum = luckyDrawBox.listenOnSelection();
        new Thread(luckyDrawBox::display).start();
        removeChildComponent(luckyDrawBox);
        return cardNumbers[reNum];
    }

    public int displayInJailAskBox(int index){
        InJailAskBox inJailAskBox = new InJailAskBox(index+1, players[index].getNameString());
        addChildComponent(inJailAskBox);
        int reNum = inJailAskBox.listenOnSelection();
        removeChildComponent(inJailAskBox);
        return reNum;
    }

    public int displayDoubleDiceBox(int index, int[] diceNumbers, boolean isMandatory){
        DoubleDiceBox doubleDiceBox = new DoubleDiceBox(index+1,
                players[index].getNameString(), diceNumbers[0], diceNumbers[1], isMandatory);
        addChildComponent(doubleDiceBox);
        new Thread(doubleDiceBox::listenOnSelection).start();
        removeChildComponent(doubleDiceBox);
        if (diceNumbers[0]==diceNumbers[1])
            return 1;
        return 0;
    }

    public void setHost(int squareIndex, int PlayerIndex){
        squareFronts[squareIndex].setHost(PlayerIndex + 1);
    }

    public void makeMovement(int index, int step){
        squareFronts[players[index].getPositionID()-1].removePlayer(index+1);

        for (int i=1; i<step;i++){
            if (players[index].getPositionID() + i <= 20)
                squareFronts[players[index].getPositionID()-1+i].passBy(index+1);
            else
                squareFronts[players[index].getPositionID()-1+i-20].passBy(index+1);
        }

        if (players[index].getPositionID() + step <= 20)
            squareFronts[players[index].getPositionID()-1+step].addPlayer(index+1);
        else
            squareFronts[players[index].getPositionID()-1+step-20].addPlayer(index+1);
    }

    public void goToJailMove(int index){
        squareFronts[players[index].getPositionID()-1].removePlayer(index+1);
        squareFronts[5].addPlayer(index+1);
    }


    private final int[][] squarePositions = {
            {93, 31},
            {76, 31},
            {59, 31},
            {42, 31},
            {25, 31},
            { 8, 31},
            { 8, 26},
            { 8, 21},
            { 8, 16},
            { 8, 11},
            { 8,  6},
            {25,  6},
            {42,  6},
            {59,  6},
            {76,  6},
            {93,  6},
            {93, 11},
            {93, 16},
            {93, 21},
            {93, 26}
    };

    private final int[][] playerPositions = {
            { 1,  1},
            {49,  1},
            {98,  1},
            { 1, 37},
            {49, 37},
            {98, 37}
    };
}
