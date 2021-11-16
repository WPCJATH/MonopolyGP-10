package monopoly.View;

import java.io.Console;
import java.util.concurrent.TimeUnit;

public class KeyboardListener extends Thread{
    private boolean isContinue;
    private char[] currentInputSequence;
    private final Console console;
    private boolean pureEnterCaptured;
    private boolean isListenOnPause;
    private boolean pauseCaptured;

    public KeyboardListener(){
        super();
        isContinue = true;
        console = System.console();
        pureEnterCaptured = false;
        isListenOnPause = false;
        pauseCaptured = false;
    }

    public void setStop(){
        isContinue = false;
    }

    public void listenOnPause(){
        isListenOnPause = true;
        clear();
        while (true){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (currentInputSequence==null  || currentInputSequence.length==0) continue;
            char charCaptured = currentInputSequence[currentInputSequence.length-1];
            if (charCaptured==127) break;
            if (charCaptured=='p' || charCaptured=='P') break;
        }
        isListenOnPause = false;
        pauseCaptured =true;
    }

    public void setUnPause(){
        pauseCaptured = false;
    }

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

    public char listenCharInput(){
        clear();
        char charCaptured;
        while (true){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (pauseCaptured) continue;
            if (currentInputSequence==null || currentInputSequence.length==0){
                if (pureEnterCaptured){
                    pureEnterCaptured = false;
                    return 10;
                }
            }
            else{
                charCaptured = currentInputSequence[currentInputSequence.length-1];
                if (isListenOnPause && (charCaptured=='p' || charCaptured=='P')){
                    clear();
                    continue;
                }
                return charCaptured;
            }
        }
    }

    public void clearCurrentListenMethods(){
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