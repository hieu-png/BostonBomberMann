package bomber.Item;

import bomber.Game;
import bomber.StillObject.Tile;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.gameFunction.Sound;

public abstract class Item extends Tile {

    protected boolean isCollected = false;
    public Item(String pathToImage,int x,int y) {
        super(pathToImage);
        this.x = x;
        this.y = y;
        this.canBePassed = true;
    }
    public abstract void doThisWhenCollided();
    public boolean collided(Entity other) {
        if(other instanceof Player) {
            if(!isCollected && this.isCollidedWith(other)) {
                doThisWhenCollided();
                destroy();
                isCollected = true;
            }
        }
        return false;
    }
}
