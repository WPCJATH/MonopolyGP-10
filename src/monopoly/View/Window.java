package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;
import monopoly.Model.Player;
import monopoly.Model.SquareBackend;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The class display and control the whole window of the game
 */
public class Window extends Thread{
    // fixed width and height
    public static final int width = 116;
    public static final int height = 41;

    private long startTime;
    // per singleFrameDuration a refresh
    private final long singleFrameDuration;

    // the most basic widget in the windows, either mainPage or gamePage
    public Widget centralWidget;
    public MainPage mainPage;
    public GamePage gamePage;
    private boolean isOnMainPage;

    private boolean isContinue;

    public Window() {
        startTime = 0;
        centralWidget = new Widget(width, height, 0, 0);
        centralWidget.initialContent();
        singleFrameDuration = (long)(1000*(1/Configs.fps));
        isContinue = true;

        mainPage = new MainPage();
        centralWidget.addChildComponent(mainPage);
        isOnMainPage = true;

    }

    /**
     * Recursively update all the widgets in the window
     * */
    public void update() throws ConcurrentModificationException {
        if (!centralWidget.isChanged()) return;
        centralWidget.update();
        printContent(centralWidget.getContent());
    }

    /**
     * keep refresh during the running of the game software
     * */
    @Override
    public void run(){
        while(isContinue){
            startTime = new Date().getTime();

            try{
                update();
            }catch (ConcurrentModificationException ignored){}


            try {
                TimeUnit.MILLISECONDS.sleep(singleFrameDuration - (new Date().getTime()- startTime));
            } catch (InterruptedException ignored) {}
        }
        // ANSI code to move the cursor
        if (Configs.displayMode.equals("refresh")){
            System.out.print("\033[2J");
        }
        System.out.println("Push one more enter to terminate.");
    }

    // set stop by controller to terminate
    public void setStop(){isContinue = false;}

    // recursively called function to listen on the user selection
    public int listenOnSelection(){
        if (isOnMainPage)
            return mainPage.listenOnSelection();
        else
            return gamePage.listenOnSelection();
    }

    // Switch to the game page
    public void goToGamePage(Player[] players, SquareBackend[] squareBackends){
        if (!isOnMainPage) return;
        centralWidget.removeChildComponent(mainPage);
        gamePage = new GamePage(players, squareBackends);
        gamePage.setAllPlayerPositions();
        centralWidget.addChildComponent(gamePage);
        isOnMainPage = false;
    }

    // Switch to the menu page
    public void goToMenuPage(){
        if (isOnMainPage) return;
        centralWidget.removeChildComponent(gamePage);
        mainPage = new MainPage();
        centralWidget.addChildComponent(mainPage);
        isOnMainPage = true;
    }

    // print the content to the screen once called
    public static void printContent(char[][] content){
        for (char[] line: content){
            System.out.println(line);
        }
        // ANSI code to move the cursor
        if (Configs.displayMode.equals("refresh")){
            System.out.print("\033["+height+"A");
        }
    }

    // debug testing
    public static void main(String[] args){

        KeyboardListener kbl = new KeyboardListener();
        GlobalController.keyboardListener = kbl;
        kbl.start();

        Player player1 = new Player(1000, 1, "Xavier1");
        Player player2 = new Player(200, 2, "Xavier2");
        Player player3 = new Player(6000, 3, "Xavier3");
        Player player4 = new Player(10, 4, "Xavier4");
        Player player5 = new Player(-500, 5, "Xavier5");
        Player player6 = new Player(-1000, 6, "Xavier6");
        Player[] players = new Player[]{player1, player2, player3, player4, player5, player6};
        Configs configs = new Configs();
        SquareBackend[] squareBackends = configs.getSquareBackends();

        GlobalController.preLoadModels = new PreLoadModels();
        Window win = new Window();
        GlobalController.window = win;

        GamePage gamePage = new GamePage(players, squareBackends);

        win.centralWidget.addChildComponent(gamePage);
        win.start();


        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException ignored) {}
        win.setStop();
        kbl.setStop();
        try {win.join();} catch (InterruptedException ignored) {}
        try {kbl.join();} catch (InterruptedException ignored) {}


    }

}



