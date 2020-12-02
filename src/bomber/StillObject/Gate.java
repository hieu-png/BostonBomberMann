package bomber.StillObject;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.gameFunction.Sound;

import java.util.List;

public class Gate extends Tile {
    Game game;
    List<Entity> entityList;
    public Gate(Game game) {
        super(System.getProperty("user.dir") + "\\src\\texture\\map\\7.png");
        this.game = game;
        this.canBePassed = true;
        entityList = game.map.getEntityList();
    }


    @Override
    public void update() {
        entityList.forEach(e -> {
            if(e instanceof Player) {
                if(isCollidedWith(e))
                    game.setGatePassed(true);
                else
                    game.setGatePassed(false);
            }
        });
    }

    @Override
    public boolean takeDamage(int damage) {
        return super.takeDamage(0);
    }
}

