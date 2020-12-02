package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class HpPlayerItem extends Item {
    private boolean isCollected = false;
    public HpPlayerItem(int x,int y) {
        super(System.getProperty("user.dir") + "\\src\\texture\\flameAll.png",x,y);
    }

    public boolean collided(Entity other) {
        if(other instanceof Player) {
            if(!isCollected && this.isCollidedWith(other)) {
                Sound.getItem();
                Game.HpUp(1);
                destroy();
                isCollected = true;
            }
        }
        return false;
    }


}
