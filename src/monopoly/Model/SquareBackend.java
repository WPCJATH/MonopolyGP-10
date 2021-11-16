package monopoly.Model;

public class SquareBackend {
    private final int positionID;
    private final String name;
    private final SquareType type;

    public SquareBackend(SquareType type, int positionID, String name){
        this.type = type;
        this.positionID = positionID;
        this.name = name;
    }

    public int getPositionID() {
        return positionID;
    }


    public String getName() {
        return name;
    }

    public SquareType getType() {
        return type;
    }

    public int getPrice() {
        return -1;
    }

    public int getRent() {
        return -1;
    }

    public boolean hasHost()
    {
        return false;
    }

    public int getHostID() {
        return -1;
    }

    public void setHostID(int hostID) {}

    public  int[] luckyDraw(){ return new int[0];}
}
