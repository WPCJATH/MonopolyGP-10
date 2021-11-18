package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;
import monopoly.Model.Player;
import monopoly.Model.SquareBackend;
import monopoly.Model.SquareType;

import java.util.concurrent.TimeUnit;

public class GamePage extends Widget{
    private final Player[] players;
    private final PlayerBar[] playerBars;
    private final SquareBackend[] squareBackends;
    private final SquareFront[] squareFronts;
    private final InJailFront inJailFront;
    private StateBar stateBar;
    private Timer timer;
    private final MessageBar messageBar;
    private boolean isPaused;
    private Boolean isContinue;


    public GamePage(Player[] players, SquareBackend[] squares) {
        super(-1, -1, 0, 0);
        setContent(GlobalController.preLoadModels.gameBoard);

        this.players = players;
        this.squareBackends = squares;

        Label incomeTaxLabel = new Label(2, 51, 33, String.valueOf(Configs.taxRate));
        Label goInitialMoneyLabel = new Label(4, 98, 33, String.valueOf(Configs.initialFunding));
        addChildComponent(incomeTaxLabel);
        addChildComponent(goInitialMoneyLabel);


        squareFronts = new SquareFront[20];
        for (int i=0; i<20; i++){
            if (squareBackends[i].getType()==SquareType.PROPERTY)
                squareFronts[i] = new SquareFront(squarePositions[i][0], squarePositions[i][1], squareBackends[i]);
            else
                squareFronts[i] = new SquareFront(squarePositions[i][0], squarePositions[i][1]);
            addChildComponent(squareFronts[i]);
        }

        Label[] moneyChangeAnimationLabels = new Label[]{
                new Label(5, 19, 2),
                new Label(5, 67, 2),
                new Label(5, 92, 2),
                new Label(5, 19, 38),
                new Label(5, 67, 38),
                new Label(5, 92, 38),
        };

        playerBars = new PlayerBar[6];
        for (int i=0; i<6; i++){
            if (i < players.length){
                playerBars[i] = new PlayerBar(playerPositions[i][0],
                        playerPositions[i][1], players[i], moneyChangeAnimationLabels[i]);
            }
            else{
                playerBars[i] = new PlayerBar(playerPositions[i][0], playerPositions[i][1]);
            }
            addChildComponent(playerBars[i]);
        }

        for (int i=0; i<players.length; i++){
            addChildComponent(moneyChangeAnimationLabels[i]);
        }

        inJailFront = new InJailFront(12, 31);
        addChildComponent(inJailFront);

        messageBar = new MessageBar();

        isPaused = false;
        isContinue = true;
    }

    public void setPaused(){
        isPaused = true;
        timer.setPaused();
    }

    public void pauseReleased(){
        isPaused = false;
        timer.releasePause();
    }

    public void setTerminated(){
        isContinue = false;
        terminateStateBar();
        for (int i=0; i<players.length;i++)
            playerBars[i].setUnselected();
    }

    public boolean waitForPause(){
        while (isPaused){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (!isContinue) return true;
        }
        return false;
    }

    public void setTimer(Timer timer){
        stateBar = new StateBar(timer);
        addChildComponent(stateBar);
        new Thread(stateBar).start();
        this.timer = timer;
        // System.out.println("Timer is started.");
    }

    public void setPlayerBarSelected(int index){
        new Thread(() -> playerBars[index].setSelected()).start();
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

    private void startWaitListenThread(){
        new Thread(() -> {
            while (true){
                if (timer.isCountDownTerminated() || !isContinue){
                    GlobalController.keyboardListener.clearCurrentListenMethods();
                    break;
                }
                try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}}
        }).start();
    }

    private void listenOnNoReturnSelection(Widget box, int index){
        timer.timerStart(Configs.waitForSelectionTime);
        if (!players[index].isRobot()){
            startWaitListenThread();
            box.listenOnSelection();
        }
        else
            try {TimeUnit.SECONDS.sleep(Configs.robotPendingTime);} catch (InterruptedException ignored) {}
        timer.stopCountDown();
    }

    public int displaySingleDiceBox(int index, int number){
        SingleDiceBox singleDiceBox= new SingleDiceBox(index+1, players[index].getNameString(), number);
        addChildComponent(singleDiceBox);
        listenOnNoReturnSelection(singleDiceBox, index);
        if (waitForPause()) return -2;
        singleDiceBox.rollDice();
        if (waitForPause()) return -2;
        removeChildComponent(singleDiceBox);
        return 1;
    }

    public int displayDoubleDiceBox(int index, int[] diceNumbers, boolean isMandatory){
        DoubleDiceBox doubleDiceBox = new DoubleDiceBox(index+1,
                players[index].getNameString(), diceNumbers[0], diceNumbers[1], isMandatory);
        addChildComponent(doubleDiceBox);
        listenOnNoReturnSelection(doubleDiceBox, index);
        if (waitForPause()) return -2;
        doubleDiceBox.rollDice();
        if (waitForPause()) return -2;
        removeChildComponent(doubleDiceBox);
        if (diceNumbers[0]==diceNumbers[1]) return 1;
        return 0;
    }

    private int listenOnReturnSelection(Widget box, int index){
        int reNum = -1;
        timer.timerStart(Configs.waitForSelectionTime);
        if (!players[index].isRobot()){
            startWaitListenThread();
            reNum = box.listenOnSelection();
        }
        else{
            try {TimeUnit.SECONDS.sleep(Configs.robotPendingTime);} catch (InterruptedException ignored) {}
        }
        timer.stopCountDown();
        return reNum;
    }

    public int displayPropertyAskBox(int playerIndex, int squareIndex){
        PropertyAskBox propertyAskBox = new PropertyAskBox(
                squareBackends[squareIndex], playerIndex + 1, players[playerIndex].getNameString());
        addChildComponent(propertyAskBox);

        if (waitForPause()) return -2;
        int reNum = listenOnReturnSelection(propertyAskBox, playerIndex);
        if (waitForPause()) return -2;
        if (reNum==-1){
            if (players[playerIndex].ifBuy(squareBackends[squareIndex])){
                reNum = 1;
                propertyAskBox.OnSelection(0);
            }
            else{
                reNum = 0;
                propertyAskBox.OnSelection(1);
            }
        }
        if (waitForPause()) return -2;
        removeChildComponent(propertyAskBox);
        return reNum;
    }

    public int displayLuckyDrawBox(int index, int[] cardNumbers){
        LuckyDrawBox luckyDrawBox = new LuckyDrawBox(index+1, players[index].getNameString(), cardNumbers);
        addChildComponent(luckyDrawBox);

        if (waitForPause()) return -2;
        int reNum = listenOnReturnSelection(luckyDrawBox, index);
        if (waitForPause()) return -2;
        if (reNum==-1){
            reNum = players[index].chooseCard(0, cardNumbers.length);
            luckyDrawBox.OnSelection(reNum);
        }

        if (waitForPause()) return -2;
        luckyDrawBox.display();

        if (waitForPause()) return -2;
        removeChildComponent(luckyDrawBox);
        return cardNumbers[reNum];
    }

    public int displayInJailAskBox(int index){
        InJailAskBox inJailAskBox = new InJailAskBox(index+1, players[index].getNameString());
        addChildComponent(inJailAskBox);

        if (waitForPause()) return -2;
        int reNum = listenOnReturnSelection(inJailAskBox, index);
        if (waitForPause()) return -2;
        if (reNum==-1){
            if (players[index].isReleaseOnBail()){
                reNum = 1;
            }
            else{
                reNum = 0;
            }
            inJailAskBox.OnSelection(reNum);
        }

        if (waitForPause()) return -2;
        removeChildComponent(inJailAskBox);
        return reNum;
    }

    public int displayRanListBox(){
        RankListBox rankListBox = new RankListBox(players);
        addChildComponent(rankListBox);
        rankListBox.listenOnSelection();
        removeChildComponent(rankListBox);
        return 1;
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


    public void setHost(int squareIndex, int PlayerIndex){
        squareFronts[squareIndex].setHost(PlayerIndex + 1);
    }

    public void makeMovement(int index, int steps){
        if (steps==0) return;
        int originPositionID = players[index].getPositionID();
        if (originPositionID + steps > 21)
            throw new IllegalArgumentException("Cannot pass go through single call.");

        squareFronts[originPositionID-1].removePlayer(index+1);
        if (originPositionID + steps == 21){
            for (int i=0; i<steps; i++)
                squareFronts[originPositionID+i-1].passBy(index+1);
            squareFronts[originPositionID+steps-1-20].passBy(index+1);
            squareFronts[originPositionID+steps-1-20].addPlayer(index+1);
        }
        else {
            for (int i=0; i<=steps; i++)
                squareFronts[originPositionID+i-1].passBy(index+1);
            squareFronts[originPositionID+steps-1].addPlayer(index+1);
        }
    }

    public void goToJailMove(int index){
        squareFronts[players[index].getPositionID()-1].removePlayer(index+1);
        inJailFront.addPlayer(index+1);
    }

    public void setOutOfJail(int index){
        inJailFront.removePlayer(index+1);
        squareFronts[5].addPlayer(index+1);
    }

    public void setRound(int round){
        stateBar.setRound(round);
    }

    public void terminateStateBar(){
        stateBar.setStop();
    }

    public void removePlayer(int index){
        squareFronts[players[index].getPositionID()-1].removePlayer(index+1);
    }


    public void clearHost(int positionIndex){
        squareFronts[positionIndex].clearHost();
    }

    public void roundMessage(int round){
        addChildComponent(messageBar);
        messageBar.roundBegins(round);
        removeChildComponent(messageBar);
    }

    public void turnsMessage(int id){
        addChildComponent(messageBar);
        messageBar.playerTurn(id);
        removeChildComponent(messageBar);
    }

    public void dealDoneMessage(){
        addChildComponent(messageBar);
        messageBar.dealIsDone();
        removeChildComponent(messageBar);
    }

    public void goJailMessage(int id){
        addChildComponent(messageBar);
        messageBar.goToJail(id);
        removeChildComponent(messageBar);
    }

    public void brokeMessage(int id){
        addChildComponent(messageBar);
        messageBar.broke(id);
        removeChildComponent(messageBar);
    }

    public void releasedMessage(){
        addChildComponent(messageBar);
        messageBar.released();
        removeChildComponent(messageBar);
    }

    public void failedMessage(){
        addChildComponent(messageBar);
        messageBar.failed();
        removeChildComponent(messageBar);
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
