package monopoly.Model;

/**
 * Some configuration of the game.
 */
public class Configs {
    // The minimum number of players in the game. Do not change.
    public static final int minimumPlayerNumber = 2;
    // The maximum number of players in the game. Do not change.
    public static final int maximumPlayerNumber = 6;
    // The minimum human player in the game. Do not change.
    public static final int minimumHumanPlayerNumber = 1;
    // The maximum length of name apearing in game. Do not change.
    public static final int nameLengthBound = 10;


    // The max round of the game, the game will automaticly terminate when reaching this round number.
    public static final int maxRoundNumber = 100;
    // The time in seconds waiting for player's selection. If time is up, the game will help the user make operation
    // immediately.
    public static final int waitForSelectionTime = 30;
    // The time in seconds pengding the robot players' operations to aviod too fast to be seen.
    public static final int robotPendingTime = 5;
    // The time in seconds of dice animation playing.
    public static final int diceAnimationDisplayTime = 2;
    // The time in seconds of the message box (i.e. Round XX, Player X's Turn...) on Screen.(In main thread)
    public static final int messageOccupyTime = 5;


    // The sides of the number shown in game. can be set to (1-6)
    public static int DiceSideNumber = 4;
    // The minimum money amount when doing Chance lucky draw.
    public static int minimumChanceMoney = -300;
    // The maximum money amount when doing Chance lucky draw
    public static int maximumChanceMoney = 200;
    // The fine when getting out of jail by paying.
    public static int BailFee = 150;
    // The initial money of players, will be shown on Go sqaure.
    public static int initialFunding = 1500;
    // The tax rate of income tax
    public static int taxRate = 10;

    // 0 for level low robot, 100 for level mid robot, 300 for level high robot.
    // The robot on makes pay when aftering paying, their money left still more than the minimum tolerance money
    public static final int[] robotMinimumToleranceMoney = {0, 100, 300};

    // Arrays for square backend in Model module
    public static SquareBackend[] squareBackends;

    // The refresh rate of the game window. Do not change unless the interface is sluggish
    public static final double fps = 10;
    // The game runs dumb in append module. Better not change it.
    public static final String displayMode = "refresh"; // "append"

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
