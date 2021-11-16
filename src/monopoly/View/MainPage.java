package monopoly.View;

import monopoly.Controller.GlobalController;

public class MainPage extends Widget{
    private final MenuPage menuPage;
    private final Widget menuTag;

    public SettingNumberPage settingPage1;
    public SettingNamePage settingPage2;

    private final Widget settingTag;
    private int pageIndex;

    public MainPage(){
        super(Window.width, Window.height, 0, 0);
        setContent(GlobalController.preLoadModels.mainPage.clone());

        menuTag = new Widget(-1, -1, 45, 18);
        menuTag.setContent(GlobalController.preLoadModels.menuTag);
        addChildComponent(menuTag);
        menuPage = new MenuPage( 34, 23);
        addChildComponent(menuPage);

        pageIndex = 0;

        settingTag = new Widget(-1, -1, 40, 17);
        settingTag.setContent(GlobalController.preLoadModels.settingTag);
        settingPage1 = new SettingNumberPage(34, 23);
        settingPage2 = new SettingNamePage(34, 23);
    }

    public String[] getPlayerNames(){
        if (pageIndex!=2)
            throw new IllegalCallerException("Player name not ensured");
        return settingPage2.getPlayerNames();
    }

    public int getPlayerNumber(){
        if (pageIndex!=2)
            throw new IllegalCallerException("Cannot get at this time.");
        return settingPage1.getPlayerNumber();
    }

    public int getRobotLevel(){
        if (pageIndex!=2)
            throw new IllegalCallerException("Cannot get at this time.");
        return settingPage1.getRobotLevel();
    }


    public void MoveToSettingPage1(){
        if (!(pageIndex==0 || pageIndex==2)) return;
        clear();
        addChildComponent(settingPage1);
        addChildComponent(settingTag);
        if (pageIndex==2) settingPage2.resetSelection();
        pageIndex = 1;
    }

    public void MoveToSettingPage2(){
        if (!(pageIndex==1)) return;
        clear();
        addChildComponent(settingPage2);
        addChildComponent(settingTag);
        pageIndex = 2;
    }

    public void MoveToMenuPage(){
        if (!(pageIndex==1)) return;
        clear();
        addChildComponent(menuTag);
        addChildComponent(menuPage);
        pageIndex = 0;
        settingPage1.resetSelection();
    }

    @Override
    public int listenOnSelection(){
        if (pageIndex==0) return menuPage.listenOnSelection();
        if (pageIndex==1) return settingPage1.listenOnSelection();
        if (pageIndex==2) return settingPage2.listenOnSelection();
        throw new IndexOutOfBoundsException("Illegal page number.");
    }

}
