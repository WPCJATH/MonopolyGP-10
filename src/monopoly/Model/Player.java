package monopoly.Model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Player {
    private int moneyAmount;
    private final int playerID;
    private final String nameString;
    private boolean isBankrupt;
    public InPrisonState inPrisonState;
    private int positionID;
    private ArrayList<Integer> propertyIds;


    private final Random randomNo;
    private int robotLevel = 1;
    private boolean isRobot = false;


    public Player(int moneyAmount, int playerID, String nameString) {
        this(moneyAmount,playerID,nameString,1,false, InPrisonState.FREE.name());
    }

    public Player(int moneyAmount, int playerID, String nameString, boolean isBankrupt) {
        this(moneyAmount,playerID,nameString,1,isBankrupt, InPrisonState.FREE.name());
    }

    public Player(int moneyAmount, int playerID, String nameString, int position,
                  boolean isBankrupt, String inPrisonState) {
        this.moneyAmount = moneyAmount;
        this.playerID = playerID;
        this.nameString = nameString;
        this.positionID = position;
        this.isBankrupt = isBankrupt;
        this.inPrisonState = InPrisonState.parseState(inPrisonState);
        this.randomNo = new Random();
        this.propertyIds = new ArrayList<>();
    }

    public boolean onGoingIncomeTax() {
        setMoney(getMoney() - IncomeTaxSquare.calculateTax(getMoney()));
        return !isBankrupt;
    }

    public boolean onGoingGo() {
        setMoney(getMoney() + Configs.initialFunding);
        return true;
    }

    public boolean onGoingProperty(SquareBackend property) {
        if (property.hasHost()) {
            if (property.getHostID() == playerID)
                return true;
            setMoney(getMoney() - property.getRent());
            return true;
        }
        return getMoney() < property.getPrice();
    }

    public boolean onBuyingProperty(SquareBackend property) {
        if (property.hasHost())
            throw new IllegalArgumentException("Already have a host.");
        if (getMoney() < property.getPrice())
            throw new IllegalCallerException("Cannot afford.");
        setMoney(getMoney() - property.getPrice());
        property.setHostID(playerID);
        propertyIds.add(property.getPositionID());
        return true;
    }

    public boolean onGoingPrison() {
        updateInPrison();
        return true;
    }

    public boolean onStayingPrison() {
        if (InPrisonState.isOutNextRound(inPrisonState)) {
            setMoney(getMoney() - Configs.BailFee);
            setOutPrison();
            return true;
        }
        return false;
    }

    public boolean onGoingChance() {
        return true;
    }

    public boolean onGoingFreeParking() {
        return true;
    }

    public boolean onGoingJustVisiting() {
        return true;
    }


    public boolean ifBuy(SquareBackend squareBackend) {
        int diff = getMoney() - squareBackend.getPrice();
        return (diff >= Configs.robotMinimumToleranceMoney[robotLevel]);
    }

    public boolean isReleaseOnBail() {
        int diff = getMoney() - Configs.BailFee;
        return (diff >= Configs.robotMinimumToleranceMoney[robotLevel]);
    }

    public int chooseCard(int lowerBound, int upperBound) {
        return randomNo.nextInt(upperBound - lowerBound) + lowerBound;
    }


    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt() {
        isBankrupt = true;
        propertyIds = new ArrayList<>();
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

    public boolean IsInPrison() {
        return InPrisonState.isInJail(inPrisonState);
    }

    public void setMoney(int newMoneyAmount) {
        if (isBankrupt() && newMoneyAmount < getMoney())
            throw new IllegalCallerException("Already Broken.");
        if (isBankrupt())
            return;
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

    public int getPropertyNumber() {
        return propertyIds.size();
    }

    public void setPropertyIds(ArrayList<Integer> propertyIds) {
    }

    public void setRobot() {
        isRobot = true;
    }

    public boolean isRobot() {
        return isRobot;
    }

    public ArrayList<Integer> getPropertyIds() {
        return propertyIds;
    }

    public int getRobotLevel() {
        return robotLevel;
    }

    public void setRobotLevel(int robotLevel) {
        this.robotLevel = robotLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return moneyAmount == player.moneyAmount && playerID == player.playerID && isBankrupt == player.isBankrupt &&
                positionID == player.positionID && robotLevel == player.robotLevel && isRobot == player.isRobot &&
                Objects.equals(nameString, player.nameString) && inPrisonState == player.inPrisonState &&
                Objects.equals(propertyIds, player.propertyIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moneyAmount, playerID, nameString, isBankrupt, inPrisonState, positionID, propertyIds, robotLevel, isRobot);
    }
}
