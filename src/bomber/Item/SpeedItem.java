package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class SpeedItem extends Item {
    private boolean isCollected = false;
    public SpeedItem(int x,int y) {
        super(System.getProperty("user.dir") + "\\src\\texture\\flameNorth.png",x,y);
    }

    public boolean collided(Entity other) {
        if(other instanceof Player) {
            if(!isCollected && this.isCollidedWith(other)) {
                Sound.getItem();
                Game.speedUp(6);
                destroy();
                isCollected = true;
            }
        }
        return false;
    }




}
