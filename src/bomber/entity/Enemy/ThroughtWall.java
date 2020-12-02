package bomber.entity.Enemy;

import bomber.Game;

public class ThroughtWall extends Enemy{
    public ThroughtWall() {
        super("balloon");
    }
    public void start() {
        this.health = 1;
        this.setSpeed(1);
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
    public boolean checkIfTileEmpty(double x, double y) {
        if(x == Game.WIDTH-1 || y == Game.HEIGHT-1 || x ==0 || y == 0) return false;
        return true;
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
