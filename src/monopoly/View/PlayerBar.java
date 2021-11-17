package monopoly.View;

import monopoly.Model.Player;

import java.util.concurrent.TimeUnit;

public class PlayerBar extends Widget{
    private int id;
    private Label idLabel;
    private Label moneyLabel;
    private Label stateLabel;
    private Player player;
    private int lastTimeMoneyAmount;
    private boolean isContinue;
    private Label moneyChangeLabel;
    private boolean isOccupied;

    public PlayerBar(int x, int y, Player player, Label moneyChangeLabel) {// Jail Rd1
        super(17, 3, x, y);
        initialContent();

        this.player = player;

        id = player.getPlayerID();
        idLabel = new Label(3, 1, 0, String.valueOf(id));
        Label nameLabel = new Label(12, 4, 0, player.getNameString());
        Label label1 = new Label(15, 1, 1, "Money:      HKD");
        moneyLabel = new Label(6,7,1,String.valueOf(player.getMoney()));
        Label label2 = new Label(6, 1, 2, "State:");
        stateLabel = new Label(8, 8, 2, "active");

        addChildComponent(idLabel);
        addChildComponent(nameLabel);
        addChildComponent(label1);
        addChildComponent(moneyLabel);
        addChildComponent(label2);
        addChildComponent(stateLabel);

        lastTimeMoneyAmount = player.getMoney();
        isContinue = false;

        this.moneyChangeLabel = moneyChangeLabel;
        isOccupied = false;
    }

    public PlayerBar(int x, int y){
        super(17, 3, x, y);
        initialContent();
    }

    public void setSelected(){
        isContinue = true;
        while (isContinue) {
            idLabel.setText("→" + id + "←");
            try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException ignored) {}
            idLabel.setText(String.valueOf(id));
            try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException ignored) {}
        }
    }

    public void setUnselected(){
        isContinue = false;
        idLabel.setText(String.valueOf(id));
    }


    public void updateMoney() {
        while (isOccupied)
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}

        int diff = player.getMoney() - lastTimeMoneyAmount;
        if (diff==0) return;
        isOccupied = true;

        moneyLabel.setText(String.valueOf(player.getMoney()));
        if (diff >= 0)
            moneyChangeLabel.setText("+" + diff);
        else
            moneyChangeLabel.setText("" + diff);

        try {TimeUnit.MILLISECONDS.sleep(2000);} catch (InterruptedException ignored) {}
        moneyChangeLabel.setText("");
        lastTimeMoneyAmount = player.getMoney();

        isOccupied = false;
    }

    public void updateState(){
        if (player.IsInPrison()){
            switch (player.inPrisonState){
                case INJAILROUND1 -> stateLabel.setText("Jail Rd1");
                case INJAILROUND2 -> stateLabel.setText("Jail Rd2");
                case INJAILROUND3 -> stateLabel.setText("Jail Rd3");
            }
            return;
        }
        if (player.isBankrupt()){
            stateLabel.setText("Broke");
            idLabel.setText("✕");
            return;
        }
        stateLabel.setText("Active");
    }
}
