package monopoly.View;

import java.io.Console;
import java.util.concurrent.TimeUnit;

public class KeyboardListener extends Thread{
    private boolean isContinue;
    private char[] currentInputSequence;
    private final Console console;
    private boolean pureEnterCaptured;
    private boolean isListenToPause;
    private boolean pauseCaptured;

    public KeyboardListener(){
        super();
        isContinue = true;
        console = System.console();
        pureEnterCaptured = false;
        isListenToPause = false;
        pauseCaptured = false;
    }

    public void setStop(){
        isContinue = false;
    }

    //listen whether p is pressed
    public void listenToPause(){
        isListenToPause = true;
        clear();
        while (true){
            if (currentInputSequence==null  || currentInputSequence.length==0) continue;
            char charCaptured = currentInputSequence[currentInputSequence.length-1];
            if (charCaptured==127) break;
            if (charCaptured=='p' || charCaptured=='P') break;
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
        }
        isListenToPause = false;
        pauseCaptured =true;
    }

    public void setUnPause(){
        pauseCaptured = false;
    }

    //the pause page only
    public char listenCharInputOnPause(){
        clear();
        while ((currentInputSequence==null || currentInputSequence.length==0) && !pureEnterCaptured){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
        }
        if (pureEnterCaptured){
            pureEnterCaptured = false;
            return 10;
        }
        return currentInputSequence[currentInputSequence.length-1];
    }


    //regular input listener
    public char listenCharInput(){
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException ignored) {}
        clear();
        char charCaptured;
        while (true){
            if (pauseCaptured) continue;
            if (currentInputSequence==null || currentInputSequence.length==0){
                if (pureEnterCaptured){
                    pureEnterCaptured = false;
                    return 10;
                }
            }
            else{
                charCaptured = currentInputSequence[currentInputSequence.length-1];
                if ((charCaptured=='p' || charCaptured=='P')){
                    continue;
                }
                return charCaptured;
            }
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
        }
    }


    public void clearCurrentListenMethods(){
        clear();
        currentInputSequence = new char[]{8};
    }


    public void clearAllCurrentListenMethods(){
        clear();
        currentInputSequence = new char[]{127};
    }

    private void clear(){
        currentInputSequence = null;
        pureEnterCaptured = false;
    }

    @Override
    public void run() {
        while (isContinue){
            currentInputSequence = console.readPassword();
            System.out.print("\033[1A");
            if (currentInputSequence==null || currentInputSequence.length==0)
                pureEnterCaptured = true;
        }
    }

    public static void main(String[] args) {
        KeyboardListener keyboardListener = new KeyboardListener();
        keyboardListener.start();

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        keyboardListener.setStop();
        try {
            keyboardListener.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}