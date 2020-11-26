package bomber.gameFunction;

import java.util.Timer;

public class TimeCounter {
    //Time when system started;
    long timeStarted = System.currentTimeMillis();
    public TimeCounter() {
    }
    public void resetCounter() {
        timeStarted = System.currentTimeMillis();
    }

    public double getTime() {
        return (System.currentTimeMillis() - timeStarted) / 1000F;
    }

}
