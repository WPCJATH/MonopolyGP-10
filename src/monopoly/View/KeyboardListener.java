package monopoly.View;

import java.io.Console;
import java.util.concurrent.TimeUnit;

public class KeyboardListener extends Thread{
    private boolean isContinue;
    private char[] currentInputSequence;
    private final Console console;
    private boolean pureEnterCaptured;
    private boolean isPaused;
    private boolean isClearCurrentListenMethods;
    private boolean isClearAllCurrentListenMethods;

    public KeyboardListener(){
        super();
        isContinue = true;
        console = System.console();
        pureEnterCaptured = false;
        isPaused = false;

        isClearAllCurrentListenMethods = false;
        isClearCurrentListenMethods = false;
    }

    public void setStop(){
        isContinue = false;
    }

    //listen whether p is pressed
    public void listenToPause(){
        clear();
        while (true){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (isClearAllCurrentListenMethods){return;}
            if (currentInputSequence==null  || currentInputSequence.length==0){continue;}
            char charCaptured = currentInputSequence[currentInputSequence.length-1];
            if (charCaptured=='p' || charCaptured=='P'){break;}
        }
        isPaused =true;
    }

    public void setUnPause(){
        isPaused = false;
    }

    //the pause page only
    public char listenCharInputOnPause(){
        clear();
        while ((currentInputSequence==null || currentInputSequence.length==0) && !pureEnterCaptured){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (isClearAllCurrentListenMethods) return 127;
        }
        if (pureEnterCaptured){
            pureEnterCaptured = false;
            return 10;
        }
        return currentInputSequence[currentInputSequence.length-1];
    }


    //regular input listener
    public char listenCharInput(){
        clear();
        char charCaptured;
        while (true){
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException ignored) {}
            if (isClearAllCurrentListenMethods || isClearCurrentListenMethods) return 127;
            if (isPaused) continue;
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
        }
    }


    public void clearCurrentListenMethods(){
        isClearCurrentListenMethods = true;
        try {TimeUnit.MILLISECONDS.sleep(600);} catch (InterruptedException ignored) {}
        isClearCurrentListenMethods = false;
    }


    public void clearAllCurrentListenMethods(){
        isClearAllCurrentListenMethods = true;
        try {TimeUnit.MILLISECONDS.sleep(600);} catch (InterruptedException ignored) {}
        isClearAllCurrentListenMethods = false;
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
        KeyboardListener kbl = new KeyboardListener();
        kbl.start();

        Thread A = new Thread(){
            @Override
            public void run(){
                while (true){
                    char c = kbl.listenCharInput();
                    System.out.println((int)c + String.valueOf(c));
                    if (c==127 || c==8)
                        break;
                }
            }
        };

        Thread B = new Thread(){
            @Override
            public void run(){
                kbl.listenToPause();
                System.out.println("P entered");
            }
        };

        A.start();
        B.start();
        try {
            B.join();
            kbl.clearCurrentListenMethods();
            A.join();
            kbl.setStop();
            kbl.join();
        } catch (InterruptedException ignored) {}
    }
}