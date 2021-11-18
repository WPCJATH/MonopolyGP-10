package monopoly.Controller;

import monopoly.Model.Configs;
import monopoly.Model.DBAccessor;
import monopoly.Model.Player;
import monopoly.Model.StaticToolMethods;
import monopoly.View.KeyboardListener;
import monopoly.View.PreLoadModels;
import monopoly.View.Window;

/**
 *
 */
public class GlobalController {
    public static PreLoadModels preLoadModels;
    public static GameController gameController;
    public static KeyboardListener keyboardListener;
    public static Window window;
    public static Configs config;

    public static void onStart(){
        keyboardListener = new KeyboardListener();
        preLoadModels = new PreLoadModels();
        window = new Window();

        keyboardListener.start();
        window.start();

        boolean isContinue = true;
        while (isContinue)
        {
            switch (GameMenu()){
                case 0 -> OnGameStart();
                case 1 -> OnGameLoad();
                case 2 -> OnUserManual();
                case 3 -> isContinue = false;
            }
        }

        window.setStop();
        keyboardListener.setStop();
        try {
            window.join();
            keyboardListener.join();
        }catch (Exception ignored){}
    }

    private static void OnGameStart() {
        config = new Configs();
        if  (OnSetting()==0) return;

        Player[] players = StaticToolMethods.playersGenerator(window.mainPage.getPlayerNames(),
                window.mainPage.getPlayerNumber(), window.mainPage.getRobotLevel());

        window.goToGamePage(players, config.getSquareBackends());
        gameController = new GameController(players);

        if (gameController.gameStart()==1){
            OnGameSave();
        }
        keyboardListener.setUnPause();
        window.goToMenuPage();
    }

    private static int GameMenu() {
        return window.listenOnSelection();
    }

    private static void OnGameLoad() {
        config = new Configs();
        gameController = DBAccessor.LoadGame();
        if (gameController==null) {
            OnGameStart();
        }
        else{
            window.goToGamePage(GameController.players, config.getSquareBackends());

            if (gameController.gameStart()==1){
                OnGameSave();
            }
            keyboardListener.setUnPause();
            window.goToMenuPage();
        }
    }

    private static void OnGameSave() {
        DBAccessor.SaveGame();
    }

    private static void OnUserManual(){
        window.mainPage.displayUserManual();
    }

    private static int OnSetting() {
        window.mainPage.MoveToSettingPage1();
        while (true){
            int i = window.listenOnSelection();
            if (i==1){
                window.mainPage.MoveToSettingPage2();
                window.mainPage.settingPage2.setPlayerNumber(window.mainPage.settingPage1.humanPlayerNumber);
                i = window.listenOnSelection();
                if (i==1)
                    return 1;
                else
                    window.mainPage.MoveToSettingPage1();
            }
            else{
                window.mainPage.MoveToMenuPage();
                return 0;
            }
        }
    }

}


