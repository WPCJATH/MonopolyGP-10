package monopoly.Model;

public class Configs {
    public static final int minimumPlayerNumber = 2;
    public static final int maximumPlayerNumber = 6;
    public static final int minimumHumanPlayerNumber = 1;
    public static final int nameLengthBound = 10;
    public static final int maxRoundNumber = 100;
    public static final int waitForSelectionTime = 10;
    public static final int robotPendingTime = 5;

    public static int DiceSideNumber = 4;
    public static int minimumChanceMoney = -200;
    public static int maximumChanceMoney = 500;
    public static int BailFee = 150;
    public static int initialFunding = 500;
    public static int taxRate = 10;
    public static SquareBackend[] squareBackends;

    public static final double fps = 10;
    public static final String displayMode = "refresh"; // "append"
    public static final int diceAnimationDisplayTime = 2; //seconds

    public static final int[] robotMinimumToleranceMoney = {0, 100, 300};

    public Configs(){
        squareBackends = new SquareBackend[20];
        loadSquares();
    }

    public  SquareBackend[] getSquareBackends() {
        return squareBackends;
    }

    public Configs(int diceSides, int minChanceMoney, int maxChanceMoney,
                   int bailFee, int initialFunding, int taxRate, SquareBackend[] squareBackends)
    {
        Configs.DiceSideNumber = diceSides;
        Configs.minimumChanceMoney  = minChanceMoney;
        Configs.maximumChanceMoney = maxChanceMoney;
        Configs.BailFee = bailFee;
        Configs.initialFunding = initialFunding;
        Configs.taxRate = taxRate;
        Configs.squareBackends = squareBackends;
    }


    private void loadSquares(){
        int i = 0;
        squareBackends[i++] = new GoSquare(1);
        squareBackends[i++] = new Property(2, "Central", 800, 90);
        squareBackends[i++] = new Property(3, "Wan Chai",700, 65);
        squareBackends[i++] = new IncomeTaxSquare(4);
        squareBackends[i++] = new Property(5, "Stanley", 600, 60);
        squareBackends[i++] = new InJailOrJustVisitingSquare(6);
        squareBackends[i++] = new Property(7, "Shek O", 400, 10);
        squareBackends[i++] = new Property(8, "Mong Kok", 500, 40);
        squareBackends[i++] = new ChanceSquare(9);
        squareBackends[i++] = new Property(10, "Tsing Yi", 400, 15);
        squareBackends[i++] = new FreeParkingSquare(11);
        squareBackends[i++] = new Property(12, "Shatin", 700 ,75);
        squareBackends[i++] = new ChanceSquare(13);
        squareBackends[i++] = new Property(14, "Tuen Mun", 400, 20);
        squareBackends[i++] = new Property(15, "Tai po", 500, 25);
        squareBackends[i++] = new GoToJailSquare(16);
        squareBackends[i++] = new Property(17, "Sai Kung", 400, 10);
        squareBackends[i++] = new Property(18, "Yuen Long", 400, 25);
        squareBackends[i++] = new ChanceSquare(19);
        squareBackends[i]   = new Property(20, "Tai O", 600, 25);
    }
}
