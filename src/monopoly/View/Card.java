package monopoly.View;

import monopoly.Controller.GlobalController;

import java.util.concurrent.TimeUnit;

public class Card extends Widget{
    private boolean isSelected;
    private boolean isFlipped;
    private final Label moneyLabel;
    private final Label label1;
    private final int num;

    public Card(int x, int y, int num){
        super(-1, -1, x, y);
        setContent(GlobalController.preLoadModels.luckyDrawCardUnselected);

        this.num = num;

        moneyLabel = new Label(6, 3, 3, "????");
        label1 = new Label(6, 3, 4, "HKD");

        addChildComponent(moneyLabel);

        isSelected = false;
        isFlipped = false;
    }

    public void setSelected() {
        if (isSelected) return;
        isSelected = true;
        setContent(GlobalController.preLoadModels.luckyDrawCardSelected);
    }

    public void setUnselected(){
        if (!isSelected) return;
        isSelected = false;
        setContent(GlobalController.preLoadModels.luckyDrawCardUnselected);
    }

    public void flip(){
        if (isFlipped) return;
        isFlipped = true;
        moneyLabel.setText(String.valueOf(num));
        addChildComponent(label1);
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException ignored) {}
    }
}
