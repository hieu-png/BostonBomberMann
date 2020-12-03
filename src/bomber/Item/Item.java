package bomber.Item;

import bomber.StillObject.Tile;
import bomber.entity.Entity;
import bomber.entity.Player;

public abstract class Item extends Tile {

    protected boolean isCollected = false;

    public Item(String pathToImage, int x, int y) {
        super(pathToImage);
        this.x = x;
        this.y = y;
        this.canBePassed = true;
        this.destructible = false;
    }

    public abstract void doThisWhenCollided(Player player);

    public boolean collided(Entity other) {
        if (other instanceof Player) {
            if (!isCollected && this.isCollidedWith(other)) {
                doThisWhenCollided((Player)other);
                destroy();
                isCollected = true;
            }
        }
        return false;
    }
}
