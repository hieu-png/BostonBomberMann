package bomber.gameFunction;

import java.util.Timer;

public class TimeCounter {
    //Time when system started;
    long timeStarted = System.nanoTime();
    public TimeCounter() {

    }
    public void resetCounter() {
        timeStarted = System.nanoTime();
    }

    public double getTime() {
        return (System.nanoTime() - timeStarted) / 1000000000;
    }

}
