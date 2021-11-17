package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;

import java.beans.beancontext.BeanContext;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StateBar extends Widget implements Runnable{
    private final Label timeCountDownLabel;
    private final Label timeLabel;
    private final Label roundLabel;
    private boolean isContinue;
    private int round;
    private final Timer timer;
    private boolean isRoundChanged;
    private String lastTimerLeft;
    private String lastTime;

    public StateBar(Timer timer) {
        super(67, 1, 25, 11);
        initialContent();

        this.timer = timer;
        Label label = new Label(1, 4, 0, "s");
        Label label2 = new Label(14, 51, 0, "Round:    /" + Configs.maxRoundNumber);

        timeCountDownLabel = new Label(2, 2
                , 0, "0");
        timeCountDownLabel.setLayout("right");
        timeLabel = new Label(5, 29, 0);
        roundLabel = new Label(3, 58, 0);
        roundLabel.setLayout("right");

        isContinue = true;
        isRoundChanged = false;
        round = -1;
        lastTimerLeft = "";
        lastTime = "";

        addChildComponent(label);
        addChildComponent(label2);
        addChildComponent(timeCountDownLabel);
        addChildComponent(timeLabel);
        addChildComponent(roundLabel);
    }

    public void setRound(int round){
        this.round = round;
        isRoundChanged = true;
    }

    public void setStop(){
        isContinue =false;
    }

    @Override
    public void run(){
        while (isContinue){
            long startTime = new Date().getTime();

            if (!timer.getTimeLeft().equals(lastTimerLeft)){
                timeCountDownLabel.setText(timer.getTimeLeft());
                lastTimerLeft = timer.getTimeLeft();
            }

            if (!Timer.getTime().equals(lastTime)){
                timeLabel.setText(Timer.getTime());
                lastTime = Timer.getTime();
            }

            if (isRoundChanged){
                roundLabel.setText(String.valueOf(round));
                isRoundChanged = false;
            }

            try {TimeUnit.MILLISECONDS.sleep(1000 - startTime);} catch (InterruptedException ignored) {}
        }
    }
}
