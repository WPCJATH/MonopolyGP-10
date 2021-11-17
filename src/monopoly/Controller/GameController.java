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
            gameLoopThread.join();
            pauseListenThread.join();
            timer.setStop();
            timer.join();
        }
        catch (InterruptedException ignored) {}

        // if quit displayRankList
        return returnNum;
    }

    public void gameLoop() {
        if (round==1){
            for (int i=0; i<players.length;i++)
                onGoingHandler(i);
        }

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ignored) {}

        while (round <= Configs.maxRoundNumber){
            gamePage.setRound(round);
            for (int i=0; (i<players.length && isContinue);i++){
                if (i!=whosTurn) continue;
                if (players[i].isBankrupt()){
                    whosTurn++;
                    continue;
                }

                gamePage.setPlayerBarSelected(i);

                if (players[i].IsInPrison()){
                    onInJailHandler(i);
                }

                else{
                    int diceNum = getSingleDiceRandomNumber();
                    if (!isContinue) break;
                    gamePage.displaySingleDiceBox(i, diceNum);
                    if (!isContinue) break;
                    int landSquareIndex = makeMovement(i, diceNum);
                    if (!isContinue) break;
                    switch (squareBackends[landSquareIndex].getType()){
                        case GO                   ->      {}
                        case CHANCE               -> onChanceHandler(i);
                        case GOTOJAIL             -> onGoJailHandler(i);
                        case PROPERTY             -> onPropertyHandler(i);
                        case INCOMETAX            -> onIncomeTaxHandler(i);
                        case FREEPARKING          -> onFreeParkingHandler(i);
                        case INJAILORJUSTVISITING -> onVisitJailHandler(i);
                    }
                }
                if (!isContinue) break;
                if (players[i].isBankrupt()){
                    gamePage.removePlayer(i);
                    for (int posIndex=0; posIndex< squareBackends.length;posIndex++){
                        if (squareBackends[posIndex].hasHost() && squareBackends[posIndex].getHostID()==i+1){
                            gamePage.clearHost(posIndex);
                            squareBackends[posIndex].setHostID(-1);
                        }
                    }
                }
                gamePage.setPlayerBarUnselected(i);
                whosTurn++;

                try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException ignored) {}
            }
            if (!isContinue) break;
            whosTurn=0;
            round++;
        }
        gamePage.terminateStateBar();
        if (round>=Configs.maxRoundNumber){
            isContinue = false;
            returnNum = 2;
            GlobalController.keyboardListener.clearAllCurrentListenMethods();
        }
    }


    private void pauseListener(){
        while(true){
            GlobalController.keyboardListener.listenOnPause();
            if (!isContinue) break;
            returnNum = gamePage.displayPauseBox();
            if (returnNum==1 || returnNum==2){
                isContinue = false;
                GlobalController.keyboardListener.clearAllCurrentListenMethods();
                break;
            }
            GlobalController.keyboardListener.setUnPause();
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


    private void onInJailHandler(int index) {
        if (!players[index].onStayingPrison()){
            if (gamePage.displayInJailAskBox(index)==1){
                if (players[index].getMoney() < Configs.BailFee){
                    if (gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), true)==1){
                        players[index].setOutPrison();
                        gamePage.setOutOfJail(index);
                    }
                    else
                        players[index].updateInPrison();
                }
                else{
                    players[index].setMoney(players[index].getMoney() - Configs.BailFee);
                    players[index].setOutPrison();
                    gamePage.setOutOfJail(index);
                }
            }
            else{
                if (gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), false)==1){
                    players[index].setOutPrison();
                    gamePage.setOutOfJail(index);
                }
                else
                    players[index].updateInPrison();
            }
        }
        else{
            gamePage.setOutOfJail(index);
        }
        gamePage.upDatePlayerBar(index);
    }

    private void onIncomeTaxHandler(int index){
        players[index].onGoingIncomeTax();
        gamePage.upDatePlayerBar(index);
    }

    private void onChanceHandler(int index) {
        players[index].onGoingChance();
        int reNum = gamePage.displayLuckyDrawBox(index, squareBackends[players[index].getPositionID()-1].luckyDraw());
        players[index].setMoney(reNum + players[index].getMoney());
        gamePage.upDatePlayerBar(index);
    }

    private void onPropertyHandler(int index) {
        SquareBackend currentSquare = squareBackends[players[index].getPositionID()-1];
        if (!players[index].onGoingProperty(currentSquare)){
            int reNum = gamePage.displayPropertyAskBox(index, players[index].getPositionID()-1);
            if (reNum==1){
                players[index].onBuyingProperty(currentSquare);
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
        gamePage.goToJailMove(index);
        players[index].setPositionID(6);
        players[index].onGoingPrison();
        gamePage.upDatePlayerBar(index);
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
