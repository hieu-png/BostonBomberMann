package bomber.entity;

import bomber.gameFunction.TimeCounter;

public class Bomb extends Entity{
    public Bomb() {
        this.destructible = false;
        this.canBePassed = false;
        this.timeCounter = new TimeCounter();
    }
    TimeCounter timeCounter;
    double fuseTime = 2f;
    public void explode() {

    }
    @Override
    public void update() {
        if(timeCounter.getTime() > fuseTime) {
            explode();
        }
    }
}
