package monopoly.Controller;

import monopoly.Model.*;
import monopoly.View.GamePage;
import monopoly.View.Timer;

import java.util.concurrent.TimeUnit;


public class GameController {
    private final Dice dice;
    public final SquareBackend[] squareBackends;
    public static Player[] players;
    private int round;
    private int whosTurn;
    private final GamePage gamePage;
    private boolean isContinue;
    private boolean isPaused;
    private int returnNum;
    public Timer timer;

    public GameController(Player[] players) {
        dice = new Dice();
        squareBackends = GlobalController.config.getSquareBackends();
        GameController.players = players;
        round = 1;
        whosTurn = 0;
        gamePage = GlobalController.window.gamePage;
        isContinue = true;
    }

    private int getSingleDiceRandomNumber(){
        return dice.rollDice();
    }

    private int[] getDoubleDiceRandomNumber(){
        int[] diceNumbers = new int[2];
        diceNumbers[0] = dice.rollDice();
        diceNumbers[1] = dice.rollDice();
        return diceNumbers;
    }

    public int gameStart(){
        Thread gameLoopThread = new Thread(this::gameLoop);
        Thread pauseListenThread = new Thread(this::pauseListener);
        timer = new Timer();

        timer.start();
        gamePage.setTimer(timer);

        pauseListenThread.start();
        gameLoopThread.start();

        try {
            gameLoopThread.join(2000);
            pauseListenThread.join(2000);
            timer.setStop();
            timer.join(1000);
        }
        catch (InterruptedException ignored) {}

        if (returnNum==2)
            gamePage.displayRanListBox();
        return returnNum;
    }

    public void gameLoop() {
        if (round==1){
            for (int i=0; i<players.length;i++)
                onGoingHandler(i);
        }

        while (round <= Configs.maxRoundNumber){

            gamePage.setRound(round);
            gamePage.roundMessage(round);

            for (int i = 0; (i<players.length && !pauseCheck());i++){
                if (i!=whosTurn) continue;
                if (players[i].isBankrupt()){
                    whosTurn++;
                    continue;
                }

                gamePage.turnsMessage(i+1);
                gamePage.setPlayerBarSelected(i);

                if (onInJailHandler(i)) {
                    if (pauseCheck()) break;
                    int diceNum = getSingleDiceRandomNumber();

                    if (pauseCheck()) break;
                    gamePage.displaySingleDiceBox(i, diceNum);

                    if (pauseCheck()) break;
                    int landSquareIndex = makeMovement(i, diceNum);

                    if (pauseCheck()) break;
                    switch (squareBackends[landSquareIndex].getType()){
                        case GO                   ->      {}
                        case CHANCE               -> onChanceHandler(i);
                        case GOTOJAIL             -> onGoJailHandler(i);
                        case PROPERTY             -> onPropertyHandler(i);
                        case INCOMETAX            -> onIncomeTaxHandler(i);
                        case FREEPARKING          -> onFreeParkingHandler(i);
                        case INJAILORJUSTVISITING -> onVisitJailHandler(i);
                    }
                    if (pauseCheck()) break;

                }

                gamePage.setPlayerBarUnselected(i);

                if (pauseCheck()) break;
                if (players[i].isBankrupt()){
                    gamePage.removePlayer(i);
                    for (int posIndex=0; posIndex< squareBackends.length;posIndex++){
                        if (squareBackends[posIndex].hasHost() && squareBackends[posIndex].getHostID()==i+1){
                            gamePage.clearHost(posIndex);
                            squareBackends[posIndex].setHostID(-1);
                        }
                    }
                    gamePage.brokeMessage(i+1);
                }

                whosTurn++;
            }

            if (pauseCheck()) break;
            whosTurn=0;
            round++;
        }

        gamePage.setTerminated();
        if (round>=Configs.maxRoundNumber){
            isContinue = false;
            returnNum = 2;
            gamePage.setTerminated();
            GlobalController.keyboardListener.clearAllCurrentListenMethods();
        }
    }

    private Boolean pauseCheck(){
        while (isPaused){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (!isContinue) return true;
        }
        return false;
    }

    private void pauseListener(){
        while(true){
            GlobalController.keyboardListener.listenToPause();
            System.out.println("Pause Detected.");
            if (!isContinue) break;
            isPaused = true;
            gamePage.setPaused();
            returnNum = gamePage.displayPauseBox();
            if (returnNum==1 || returnNum==2){
                isContinue = false;
                gamePage.setTerminated();
                GlobalController.keyboardListener.clearAllCurrentListenMethods();
                break;
            }
            GlobalController.keyboardListener.setUnPause();
            gamePage.pauseReleased();
            isPaused = false;
        }
    }

    private int makeMovement(int index, int diceNum){
        int positionID = players[index].getPositionID();
        if (positionID + diceNum > 20){
            gamePage.makeMovement(index, 21 - positionID);
            onGoingHandler(index);
            players[index].setPositionID(1);
            gamePage.makeMovement(index, diceNum - (21-positionID));
            players[index].setPositionID(positionID + diceNum - 20);
            return positionID + diceNum - 21;
        }
        else{
            gamePage.makeMovement(index, diceNum);
            players[index].setPositionID(positionID + diceNum);
            return positionID + diceNum - 1;
        }
    }


    private boolean onInJailHandler(int index) {
        boolean reValue = true;
        if (!players[index].IsInPrison()) return true;

        if (!players[index].onStayingPrison()){
            int reNum = gamePage.displayInJailAskBox(index);
            if (reNum==1){
                if (players[index].getMoney() < Configs.BailFee){
                    reNum = gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), true);
                    if (reNum==1){
                        players[index].setOutPrison();
                        gamePage.setOutOfJail(index);
                        gamePage.releasedMessage();
                    }
                    else if (reNum==0){
                        players[index].updateInPrison();
                        gamePage.failedMessage();
                        reValue = false;
                    }
                    else{
                        return true;
                    }
                }
                else{
                    players[index].setMoney(players[index].getMoney() - Configs.BailFee);
                    players[index].setOutPrison();
                    gamePage.setOutOfJail(index);
                    gamePage.releasedMessage();
                }
            }
            else if (reNum==0){
                reNum = gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), false);
                if (reNum==1){
                    players[index].setOutPrison();
                    gamePage.setOutOfJail(index);
                    gamePage.releasedMessage();
                }
                else if (reNum==0){
                    gamePage.failedMessage();
                    players[index].updateInPrison();
                    reValue = false;
                }
                else
                    return true;
            }
            else{
                return true;
            }
        }
        else{
            gamePage.setOutOfJail(index);
            gamePage.releasedMessage();
        }
        gamePage.upDatePlayerBar(index);
        return reValue;
    }

    private void onIncomeTaxHandler(int index){
        players[index].onGoingIncomeTax();
        gamePage.upDatePlayerBar(index);
    }

    private void onChanceHandler(int index) {
        players[index].onGoingChance();
        int reNum = gamePage.displayLuckyDrawBox(index, squareBackends[players[index].getPositionID()-1].luckyDraw());
        if (reNum==-2) return;
        players[index].setMoney(reNum + players[index].getMoney());
        gamePage.upDatePlayerBar(index);
    }

    private void onPropertyHandler(int index) {
        SquareBackend currentSquare = squareBackends[players[index].getPositionID()-1];
        if (!players[index].onGoingProperty(currentSquare)){
            int reNum = gamePage.displayPropertyAskBox(index, players[index].getPositionID()-1);
            if (reNum==1){
                players[index].onBuyingProperty(currentSquare);
                gamePage.dealDoneMessage();
                gamePage.upDatePlayerBar(index);
                gamePage.setHost(currentSquare.getPositionID()-1, index);
            }
        }
        else{
            if (currentSquare.hasHost() && currentSquare.getHostID()!=index+1){
                players[currentSquare.getHostID()-1].setMoney(
                        players[currentSquare.getHostID()-1].getMoney() + currentSquare.getRent());
                gamePage.upDatePlayerBar(index);
                gamePage.upDatePlayerBar(currentSquare.getHostID()-1);
            }
        }
    }

    private void onGoJailHandler(int index) {
        new Thread(() -> {
            gamePage.goToJailMove(index);
            players[index].setPositionID(6);
            players[index].onGoingPrison();
            gamePage.upDatePlayerBar(index);
        }).start();
        gamePage.goJailMessage(index + 1);
    }

    private void onVisitJailHandler(int index) {
        players[index].onGoingJustVisiting();
    }
    private void onFreeParkingHandler(int index) {
        players[index].onGoingFreeParking();
    }
    private void onGoingHandler(int index) {
        players[index].onGoingGo();
        gamePage.upDatePlayerBar(index);
    }

}
