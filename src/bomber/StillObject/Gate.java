package bomber.StillObject;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.gameFunction.Sound;

public class Gate extends Tile {
    Game game;

    public Gate(Game game) {
        super(System.getProperty("user.dir") + "\\src\\texture\\map\\7.png");
        this.game = game;
        this.canBePassed = true;
    }

    public boolean collide(Entity e) {
        boolean passed = false;
        if (e instanceof Player) {
            if (game.getNumOfEnemy() == 0 && this.isCollidedWith(e)) {
                passed = true;
                Sound.getItem();
                game.getMainMenu().nextLevel();

            }
        }
        return passed;
    }

    @Override
    public boolean takeDamage(int damage) {
        return super.takeDamage(0);
    }
}

