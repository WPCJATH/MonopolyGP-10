package monopoly.Model;

import java.util.Random;

public class Player {
    private int moneyAmount;
    private final int playerID;
    private final String nameString;
    private boolean isBankrupt;
    private InPrisonState inPrisonState;
    private int positionID;
    private int propertyNumber;


    private final Random randomNo;
    private int robotLevel = 1;
    private boolean isRobot = false;


    public Player(int moneyAmount, int playerID, String nameString){
        this.moneyAmount = moneyAmount;
        this.playerID = playerID;
        this.nameString = nameString;
        positionID = 1;
        isBankrupt = false;
        inPrisonState = InPrisonState.FREE;
        propertyNumber = 0;
        randomNo = new Random();
    }

    public Player(int moneyAmount, int playerID, String nameString, boolean isBankrupt){
        this.moneyAmount = moneyAmount;
        this.playerID = playerID;
        this.nameString = nameString;
        this.isBankrupt = isBankrupt;
        randomNo = new Random();
    }

    public Player(int moneyAmount, int playerID, String nameString, int position,
                  boolean isBankrupt, String inPrisonState){
        this.moneyAmount = moneyAmount;
        this.playerID = playerID;
        this.nameString = nameString;
        this.positionID = position;
        this.isBankrupt = isBankrupt;
        this.inPrisonState = InPrisonState.parseState(inPrisonState);
        randomNo = new Random();
    }

    public boolean onGoingIncomeTax(){
        setMoney(getMoney() - IncomeTaxSquare.calculateTax(getMoney()));
        return !isBankrupt;
    }

    public boolean onGoingGo(){
        setMoney(getMoney() + Configs.initialFunding);
        return true;
    }

    public boolean onGoingProperty(SquareBackend property){
        if (!property.hasHost())
            return false;
        if (property.getHostID()==playerID)
            return true;
        setMoney(getMoney() - property.getRent());
        propertyNumber++;
        return true;
    }

    public boolean onBuyingProperty(SquareBackend property){
        if (property.hasHost())
            throw new IllegalArgumentException();
        if (getMoney() < property.getPrice())
            return false;
        setMoney(getMoney() - property.getPrice());
        property.setHostID(playerID);
        return true;
    }

    public boolean onGoingPrison(){
        updateInPrison();
        return true;
    }

    public boolean onStayingPrison(){
        if (InPrisonState.isOutNextRound(inPrisonState)){
            setMoney(getMoney() - Configs.BailFee);
            updateInPrison();
            return true;
        }
        return false;
    }

    public boolean onGoingChance(){return true;}
    public boolean onGoingFreeParking(){return true;}
    public boolean onGoingJustVisiting(){return true;}


    public boolean ifBuy() {
        return true;
    }

    public boolean isReleaseOnBail(){
        return true;
    }

    public int chooseCard(int upperBound, int lowerBound) {
        return randomNo.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }


    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(){
        isBankrupt = true;
    }

    public void updateInPrison() {
        inPrisonState = InPrisonState.goNextState(inPrisonState);
    }

    public void setOutPrison() {
        inPrisonState = InPrisonState.FREE;
    }

    public InPrisonState getPrisonState() {
        return inPrisonState;
    }

    public boolean IsInPrison(){
        return InPrisonState.isInJail(inPrisonState);
    }

    public void setMoney(int newMoneyAmount) {
        if (isBankrupt())
            throw new IllegalArgumentException();
        moneyAmount = newMoneyAmount;
        if (moneyAmount < 0)
            setBankrupt();
    }

    public int getMoney() {
        return moneyAmount;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getNameString() {
        return nameString;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public int getPropertyNumber(){
        return propertyNumber;
    }

    public void setRobot() {isRobot = true;}

    public boolean isRobot() {
        return isRobot;
    }

    public void setRobotLevel(int robotLevel) {
        this.robotLevel = robotLevel;
    }
}
