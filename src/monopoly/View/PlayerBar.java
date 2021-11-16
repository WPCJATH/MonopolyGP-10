package monopoly.View;

import monopoly.Model.Player;

import java.util.concurrent.TimeUnit;

public class PlayerBar extends Widget{
    private int id;
    private Label idLabel;
    private Label moneyLabel;
    private Label stateLabel;
    private Player player;
    private int moneyAmount;

    public PlayerBar(int x, int y, Player player) {
        super(17, 3, x, y);
        initialContent();

        this.player = player;

        id = player.getPlayerID();
        idLabel = new Label(3, 1, 0, String.valueOf(id));
        Label nameLabel = new Label(12, 4, 0, player.getNameString());
        Label label1 = new Label(15, 1, 1, "Money:      HKD");
        moneyLabel = new Label(6,7,1,String.valueOf(player.getMoney()));
        stateLabel = new Label(15, 1, 2, "active");

        addChildComponent(idLabel);
        addChildComponent(nameLabel);
        addChildComponent(label1);
        addChildComponent(moneyLabel);
        addChildComponent(stateLabel);

        moneyAmount = player.getMoney();
    }

    public void setSelected(){
        idLabel.setText("→" + id + "←");
    }

    public void setUnselected(){
        idLabel.setText(String.valueOf(id));
    }

    public PlayerBar(int x, int y, int id){
        super(17, 3, x, y);
        initialContent();
        Label idLabel = new Label(1, 2, 0, String.valueOf(id));
    }

    public void updateMoney() {
        moneyLabel.setText(String.valueOf(player.getMoney()));
        stateLabel.setLayout("right");
        int diff = player.getMoney() - moneyAmount;
        if (diff >= 0)
            stateLabel.setText("+" + diff);
        else
            stateLabel.setText("" + diff);
        try {TimeUnit.MILLISECONDS.sleep(1000);} catch (InterruptedException ignored) {}
        stateLabel.setLayout("center");
        moneyAmount = player.getMoney();
    }

    public void updateState(){
        if (player.IsInPrison()){
            stateLabel.setText("In Jail");
            return;
        }
        if (player.isBankrupt()){
            stateLabel.setText("Broke");
            return;
        }
        stateLabel.setText("Active");
    }


}
