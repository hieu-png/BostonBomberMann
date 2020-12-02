package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class BombLevelItem extends Item {
    private boolean isCollected = false;
    public BombLevelItem(int x,int y) {
        super(System.getProperty("user.dir") + "\\src\\texture\\flameEast.png",x,y);

    }

    public boolean collided(Entity other) {
        if(other instanceof Player) {
            if(!isCollected && this.isCollidedWith(other)) {
                Sound.getItem();
                Game.bombLevelUp(1);
                destroy();
                isCollected = true;
            }
        }
        return false;
    }


}
