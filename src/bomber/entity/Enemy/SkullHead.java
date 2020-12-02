package bomber.entity.Enemy;


import java.util.Random;

public class SkullHead extends Enemy{
    public SkullHead() {
        super("skullHead");strengthPoint = 15;
    }

    @Override
    public void start() {
        this.health = 1;
        Random random = new Random();
        this.setSpeed(random.nextInt(2)+1);
        canBePassed = true;
    }

    public void movement() {
        enemyAI = new EnemyAI(player,this);
        if (updateCounter > updateRate && !isMoving()) {
            switch (enemyAI.getDirection()) {
                case WEST:
                    toX--;
                    break;
                case EAST:
                    toX++;
                    break;
                case SOUTH:
                    toY++;
                    break;
                case NORTH:
                    toY--;
                    break;
                default: break;
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
        if (updateCounter > updateRate)
            updateCounter = 0;
    }

}
