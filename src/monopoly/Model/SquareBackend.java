package monopoly.Model;

/**
 * Handle the data and calculation of squares.
 */
public class SquareBackend {
    private final int positionID;
    private final String name;
    private final SquareType type;

    /**
     * Constructor.
     * @param type type of the square.
     * @param positionID position id of the square
     * @param name name of the square.
     */
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

    /**
     * @return whether the square has a host.
     */
    public boolean hasHost()
    {
        return false;
    }

    public int getHostID() {
        return -1;
    }

    public void setHostID(int hostID) {}

    /**
     * An API for son classes to implement.
     */
    public  int[] luckyDraw(){ return new int[0];}
}
