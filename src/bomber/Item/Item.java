package bomber.Item;

import bomber.StillObject.Tile;
import bomber.entity.Entity;

public abstract class Item extends Tile {

    public Item(String pathToImage,int x,int y) {
        super(pathToImage);
        this.x = x;
        this.y = y;
        this.canBePassed = true;
    }

    public abstract boolean collided(Entity other);
}
