package bomber.entity;

import bomber.Game;

import java.util.Random;

public class Needle extends Enemy {


    public Needle() {
        super("needle");
    }
    @Override
    public void start() {
        this.health = 1;
        this.setSpeed(1);
        canBePassed = true;
    }
    public void movement(){

        if(updateCounter > updateRate && !isMoving()) {

            switch (Game.randomInt(0,6)) {
                case 1: toX++;
                break;
                case 2: toY++;
                break;
                case 3: toX--;
                break;
                case 4: toY--;
                default:
                    break;
            }


        }
    }

    @Override
    public void update() {
        updateCounter++;
        updateMapInfo();
        checkPlayer();
        movement();
        move();
        enemyIdleNoise();
        if(updateCounter > updateRate)
            updateCounter = 0;
    }
}
