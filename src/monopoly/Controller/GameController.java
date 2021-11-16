package monopoly.Controller;

import monopoly.Model.*;
import monopoly.View.GamePage;

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

        pauseListenThread.start();
        gameLoopThread.start();

        try {
            gameLoopThread.join();
            pauseListenThread.join();
        }
        catch (InterruptedException ignored) {}

        // if quit displayRankList
        return returnNum;
    }

    public void gameLoop() {
        if (round==1){
            for (int i=0; i<players.length;i++)
                go(i);
        }

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ignored) {}
        while (round <= Configs.maxRoundNumber){
            for (int i=0; (i<players.length && isContinue);i++){
                if (i!=whosTurn) continue;
                if (players[i].isBankrupt()){
                    whosTurn++;
                    continue;
                }

                gamePage.setPlayerBarSelected(i);
                if (players[i].IsInPrison()){
                    inJail(i);
                }

                else{
                    int diceNum = getSingleDiceRandomNumber();
                    gamePage.displaySingleDiceBox(i, diceNum);
                    if (!isContinue) break;
                    gamePage.makeMovement(i, diceNum);
                    int positionID = players[i].getPositionID();
                    if (positionID+diceNum > 20){
                        players[i].setPositionID(players[i].getPositionID() + diceNum - 20);
                        players[i].onGoingGo();
                        gamePage.upDatePlayerBar(i);
                    }
                    players[i].setPositionID(players[i].getPositionID() + diceNum);
                    switch (squareBackends[players[i].getPositionID()-1].getType()){
                        case GO                   -> go(i);
                        case CHANCE               -> chance(i);
                        case GOTOJAIL             -> goJail(i);
                        case PROPERTY             -> property(i);
                        case INCOMETAX            -> inComeTax(i);
                        case FREEPARKING          -> freeParking(i);
                        case INJAILORJUSTVISITING -> visitJail(i);
                    }
                }
                if (!isContinue) break;
                gamePage.setPlayerBarUnselected(i);
                whosTurn++;

                try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException ignored) {}
            }
            if (!isContinue) break;
            whosTurn=0;
            round++;
        }
        isContinue = false;
        returnNum = 2;
        GlobalController.keyboardListener.clearCurrentListenMethods();
    }


    public void pauseListener(){
        while(true){
            GlobalController.keyboardListener.listenOnPause();
            if (!isContinue) break;
            returnNum = gamePage.displayPauseBox();
            if (returnNum==1 || returnNum==2){
                isContinue = false;
                GlobalController.keyboardListener.clearCurrentListenMethods();
                break;
            }
            GlobalController.keyboardListener.setUnPause();
        }
    }


    public void inJail(int index) {
        if (!players[index].onStayingPrison()){
            if (gamePage.displayInJailAskBox(index)==1){
                if (players[index].getMoney() < Configs.BailFee){
                    if (gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), true)==1)
                        players[index].setOutPrison();
                    else
                        players[index].updateInPrison();
                }
                else{
                    players[index].setMoney(players[index].getMoney() - Configs.BailFee);
                    players[index].setOutPrison();
                }
            }
            else{
                if (gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), false)==1)
                    players[index].setOutPrison();
                else
                    players[index].updateInPrison();
            }

        }
        gamePage.upDatePlayerBar(index);
    }

    public void inComeTax(int index){
        players[index].onGoingIncomeTax();
        gamePage.upDatePlayerBar(index);
    }

    public void chance(int index) {
        players[index].onGoingChance();
        int reNum = gamePage.displayLuckyDrawBox(index, squareBackends[players[index].getPositionID()-1].luckyDraw());
        players[index].setMoney(reNum + players[index].getMoney());
    }

    public void property(int index) {
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
            if (currentSquare.hasHost()){
                players[currentSquare.getHostID()-1].setMoney(
                        players[currentSquare.getHostID()-1].getMoney() + currentSquare.getRent());
                gamePage.upDatePlayerBar(index);
                gamePage.upDatePlayerBar(currentSquare.getHostID()-1);
            }
        }

    }

    public void goJail(int index) {
        gamePage.goToJailMove(index);
        players[index].setPositionID(5);
        players[index].onGoingPrison();
    }

    public void visitJail(int index) {
        players[index].onGoingJustVisiting();
    }
    public void freeParking(int index) {
        players[index].onGoingFreeParking();
    }
    public void go(int index) {
        players[index].onGoingGo();
        gamePage.upDatePlayerBar(index);
    }

}
