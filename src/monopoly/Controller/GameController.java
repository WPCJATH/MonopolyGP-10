package monopoly.Controller;

import monopoly.Model.*;
import monopoly.View.GamePage;
import monopoly.View.Timer;

import java.util.concurrent.TimeUnit;

/**
 * Controller of the game.
 */
public class GameController {
    private final Dice dice;
    public final SquareBackend[] squareBackends;
    public static Player[] players;
    public static int round;
    public static int whosTurn;
    private  GamePage gamePage;
    private boolean isContinue;
    private boolean isPaused;
    private int returnNum;
    public Timer timer;

    /**
     * Constructor.
     */
    public GameController(Player[] players) {
        dice = new Dice();
        squareBackends = GlobalController.config.getSquareBackends();
        GameController.players = players;
        GameController.round = 0;
        GameController.whosTurn = 0;
        gamePage = GlobalController.window.gamePage;

        isContinue = true;
        isPaused = false;
    }

    public void setGamePage(GamePage gamePage){
        this.gamePage = gamePage;
    }

    /**
     * @return single dice number.
     */
    private int getSingleDiceRandomNumber(){
        return dice.rollDice();
    }

    /**
     * @return double dice number.
     */
    private int[] getDoubleDiceRandomNumber(){
        int[] diceNumbers = new int[2];
        diceNumbers[0] = dice.rollDice();
        diceNumbers[1] = dice.rollDice();
        return diceNumbers;
    }

    /**
     * Start the game.
     * Open the threads of pause listener, timer.
     * Recycle these threads after gameLoop() ending.
     */
    public int gameStart(){
        Thread pauseListenThread = new Thread(this::pauseListener);
        timer = new Timer();

        timer.start();
        gamePage.setTimer(timer);

        pauseListenThread.start();
        gameLoop();

        try {
            pauseListenThread.join();
            timer.setStop();
            timer.join();
        }
        catch (InterruptedException ignored) {}

        if (returnNum==2)
            gamePage.displayRanListBox();
        return returnNum;
    }

    /**
     * Game main loop.
     */
    public  void gameLoop() {
        // round==0 means the start of the game, trigger the onGoingHandler (add money)
        if (round==0){
            for (int i=0; i<players.length;i++)
                onGoingHandler(i);
            round++;
        }
        // round!=0 means the game is reloaded, put the players in jail into the jail and set hosts for all properties
        else{
            for (int i=0;i<players.length;i++){
                if (players[i].IsInPrison()){
                    gamePage.goToJailMove(i);
                }
                gamePage.upDatePlayerBar(i);

                for (int id: players[i].getPropertyIds()){
                    squareBackends[id-1].setHostID(players[i].getPlayerID());
                }
            }

        }

        // The round loop, 1 loop for 1 round
        while (round <= Configs.maxRoundNumber){

            // Set the round number
            gamePage.setRound(round);
            // Display the round message on interface
            gamePage.roundMessage(round);

            if (pauseCheck()) break;
            for (int i = 0; i<players.length;i++){
                if (i!=whosTurn) continue; // If game is reloaded, the game might not continue with player 1
                // Skip the broken players
                if (players[i].isBankrupt()){
                    whosTurn++;
                    continue;
                }
                // Display turn message
                gamePage.turnsMessage(i+1);
                // Highlight the player on interface
                gamePage.setPlayerBarSelected(i);

                // Check and handler the prison state(if any) and check if broken
                if (onInJailHandler(i) && !players[i].isBankrupt()) {
                    if (pauseCheck()) break;
                    // Display the single dice interface
                    int diceNum = getSingleDiceRandomNumber();
                    gamePage.displaySingleDiceBox(i, diceNum);

                    // Move the player
                    if (pauseCheck()) break;
                    int landSquareIndex = makeMovement(i, diceNum);

                    // Trigger the corresponding square handler
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
                // Cancel Highlight
                gamePage.setPlayerBarUnselected(i);

                if (pauseCheck()) break;
                // Recycle the properties and remove the player from game board if the player is broken.
                if (players[i].isBankrupt()){
                    gamePage.removePlayer(i);

                    for (int id: players[i].getPropertyIds()){
                        gamePage.clearHost(id-1);
                        squareBackends[id-1].setHostID(-1);
                    }
                    // Display the broke message
                    gamePage.brokeMessage(i+1);
                }
                whosTurn++;
            }

            if (pauseCheck()) break;
            whosTurn=0;
            round++;
        }

        gamePage.setTerminated();
        // The game terminates because arriving the maximum round
        if (round>=Configs.maxRoundNumber){
            isContinue = false;
            returnNum = 2;
            GlobalController.keyboardListener.clearAllCurrentListenMethods();
        }
    }

    /**
     * Check whether the game is paused
     * */
    private Boolean pauseCheck(){
        while (isPaused){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            // if the game is asked to quit
            if (!isContinue) return true;
        }
        // if the game is asked to continue
        return false;
    }

    /**
     * Listen to the pause signal
     * */
    private void pauseListener(){
        while(true){
            GlobalController.keyboardListener.listenToPause();
            // Check is terminated
            if (!isContinue) break;
            isPaused = true;
            // Tell gamePage the game is paused
            gamePage.setPaused();
            // Display the pause message box
            returnNum = gamePage.displayPauseBox();
            if (returnNum==1 || returnNum==2){
                isContinue = false;
                gamePage.setTerminated();
                GlobalController.keyboardListener.clearAllCurrentListenMethods();
                break;
            }
            // Continue the game
            GlobalController.keyboardListener.setUnPause();
            gamePage.pauseReleased();
            isPaused = false;
        }
    }

    /**
     * Move the current player
     * */
    private int makeMovement(int index, int diceNum){
        int positionID = players[index].getPositionID();
        // Special treatment if the current player moves to a new cycle
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

    /**
     * Handle the in prison player
     * */
    private boolean onInJailHandler(int index) {
        if (!players[index].IsInPrison()) return true;

        boolean reValue = true;
        if (!players[index].onStayingPrison()){
            int reNum1;
            reNum1 = gamePage.displayInJailAskBox(index);
            // reNum1 == 1: the user choose to pay the bail
            // reNum1 == 2: the user choose to roll the double dice
            // reNum1 == other: the user is quiting the game
            if (reNum1==1){
                // If the player's money cannot afford the bail, the player will be asked to roll the double dice
                if (players[index].getMoney() < Configs.BailFee){
                    int reNum2;
                    reNum2 = gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), true);
                    // reNum2==1: the numbers of the double dice are the same, can be released
                    // reNum2==0: the numbers are not the same, move to next in jail state
                    // reNum2==other: the user is quiting the game
                    if (reNum2==1){
                        players[index].setOutPrison();
                        gamePage.setOutOfJail(index);
                        gamePage.releasedMessage();
                    }
                    else if (reNum2==0){
                            players[index].updateInPrison();
                            gamePage.failedMessage();
                            reValue = false;
                    }
                    else{
                        return false;
                    }
                }
                // Deduct money and release the player
                else{
                    players[index].setMoney(players[index].getMoney() - Configs.BailFee);
                    players[index].setOutPrison();
                    gamePage.setOutOfJail(index);
                    gamePage.releasedMessage();
                }
            }
            else if (reNum1==0){
                    int reNum3;
                    reNum3 = gamePage.displayDoubleDiceBox(index, getDoubleDiceRandomNumber(), false);
                    if (reNum3==1){
                        players[index].setOutPrison();
                        gamePage.setOutOfJail(index);
                        gamePage.releasedMessage();
                    }
                    else if (reNum3==0){
                            gamePage.failedMessage();
                            players[index].updateInPrison();
                            reValue = false;
                        }
                    else{
                        return false;
                    }
            }
            else{
                return false;
            }
        }
        else{
            gamePage.setOutOfJail(index);
            gamePage.releasedMessage();
        }

        gamePage.upDatePlayerBar(index);
        return reValue;
    }

    /**
     * Triggered when the player arrive at income tax square
     * */
    private void onIncomeTaxHandler(int index){
        players[index].onGoingIncomeTax();
        gamePage.upDatePlayerBar(index);
    }

    /**
     * Triggered when the player arrive at chance handler
     * */
    private void onChanceHandler(int index) {
        players[index].onGoingChance();
        int reNum = gamePage.displayLuckyDrawBox(index, squareBackends[players[index].getPositionID()-1].luckyDraw());
        if (reNum==-2) return; // the user is quiting the game
        players[index].setMoney(reNum + players[index].getMoney());
        gamePage.upDatePlayerBar(index);
    }

    /**
     * Triggered when the player arrive at a property square
     * */
    private void onPropertyHandler(int index) {
        SquareBackend currentSquare = squareBackends[players[index].getPositionID()-1];
        // if the player can afford the current property
        if (!players[index].onGoingProperty(currentSquare)){
            int reNum = gamePage.displayPropertyAskBox(index, players[index].getPositionID()-1);
            // reNum==1: the player want to buy the property
            if (reNum==1){
                players[index].onBuyingProperty(currentSquare);
                gamePage.dealDoneMessage();
                gamePage.upDatePlayerBar(index);
                gamePage.setHost(currentSquare.getPositionID()-1, index);
            }
        }
        // the player cannot afford the current property or the property already has a host
        else{
            // if the property has a property and the host is not the current player
            if (currentSquare.hasHost() && currentSquare.getHostID()!=index+1){
                players[currentSquare.getHostID()-1].setMoney(
                        players[currentSquare.getHostID()-1].getMoney() + currentSquare.getRent());
                gamePage.upDatePlayerBar(index);
                gamePage.upDatePlayerBar(currentSquare.getHostID()-1);
            }
        }
    }

    /**
     * Trigger when the player arrive at the go jail square
     * */
    private void onGoJailHandler(int index) {
        // Move the player to jail
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
