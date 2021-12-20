package monopoly.View;

import monopoly.Controller.GlobalController;

public class UserManual extends Widget{

    public UserManual(){
        super(-1, -1, 11, 16);
        setContent(GlobalController.preLoadModels.userManual);
    }

    @Override
    public int listenOnSelection(){
        while (true){
            if (GlobalController.keyboardListener.listenCharInput()==10)
                break;
        }
        return 0;
    }
}
