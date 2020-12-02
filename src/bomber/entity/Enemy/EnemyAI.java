package bomber.entity.Enemy;

import bomber.entity.Player;
import bomber.entity.facingDirection;

import java.util.Random;

public class EnemyAI {
    Player player;
    Enemy enemy;

    public EnemyAI(Player player, Enemy enemy) {
        this.enemy = enemy;
        this.player = player;
    }

    public facingDirection diNgang() {
        if (enemy.getX() == player.getX()
                && enemy.getY() == player.getY()) return facingDirection.STATIONARY;
        else if (enemy.getX() > player.getX()) {
            return facingDirection.WEST;
        } else if (enemy.getX() == player.getX()) {
            return diDoc();
        } else {
            return facingDirection.EAST;
        }
    }

    public facingDirection diDoc() {
        if (enemy.getX() == player.getX()
                && enemy.getY() == player.getY()) return facingDirection.STATIONARY;
        else if (enemy.getY() > player.getY()) {
            return facingDirection.NORTH;
        } else if (enemy.getY() == player.getY()) {
            return diNgang();
        } else {
            return facingDirection.SOUTH;
        }
    }

    public facingDirection getDirection() {
        Random random = new Random();
        if(random.nextInt(2) == 0) return diNgang();
        return diDoc();



    }
}
