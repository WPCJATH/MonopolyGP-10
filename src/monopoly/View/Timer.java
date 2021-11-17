package monopoly.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timer extends Thread{
    private int timeLeft;
    private boolean isTimerOn;
    private boolean isContinue;

    public Timer(){
        timeLeft = 0;
        isTimerOn = false;
        isContinue = true;
    }

    public String getTimeLeft() {
        return String.valueOf(timeLeft);
    }

    public void timerStart(int timeCountDown) {
        timeLeft = timeCountDown;
        isTimerOn = true;
    }

    public boolean isCountDownTerminated(){
        return isTimerOn;
    }

    public void setStop(){
        isContinue = false;
    }

    public void run(){
        while (isContinue){
            long startTime = new Date().getTime();
            if (timeLeft>0 && isTimerOn)
                timeLeft-=1;
            if (timeLeft<=0)
                isTimerOn = false;
            try {TimeUnit.MILLISECONDS.sleep(1000 - startTime);} catch (InterruptedException ignored) {}
        }
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("HH:mm");
        Date date = new Date();
        return sdf.format(date);
    }

}
