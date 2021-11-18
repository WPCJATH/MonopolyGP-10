package monopoly.Model;

public class Property extends SquareBackend{
    private final int price;
    private final int rent;
    private int HostID = -1;

    /**
     * Constructor.
     */
    public Property(int positionID, String name, int price, int rent){
        super(SquareType.PROPERTY, positionID, name);
        this.price = price;
        this.rent = rent;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    /**
     * @return whether current property has an owner.
     */
    public boolean hasHost()
    {
        return !(HostID==-1);
    }

    public int getHostID() {
        return HostID;
    }

    public void setHostID(int hostID) {
        HostID = hostID;
    }
}
