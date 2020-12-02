package bomber.entity.Enemy;

import bomber.Game;

public class Balloon extends Enemy {
    public Balloon() {
        super("balloon");
        strengthPoint = 20;
    }

    public void start() {
        this.health = 1;
        this.setSpeed(1);
        canBePassed = true;
    }

    public void movement() {
        enemyAI = new EnemyAI(player, this);
        if (updateCounter > updateRate && !isMoving()) {
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
    public boolean checkIfTileEmpty(double x, double y) {
        if (x <= Game.WIDTH - 1 && y <= Game.HEIGHT - 1 && x > 0 && y > 0)
            return true;
        else
            return false;
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
