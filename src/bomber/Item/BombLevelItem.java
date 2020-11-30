package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class BombLevelItem extends Item {
    private boolean isCollected = false;
    public BombLevelItem(String pathOfImage) {
        super(pathOfImage);
    }

    @Override
    public boolean isCollidedWith(Entity other) {
        if(other instanceof Player) {
            if(!isCollected) {
                Sound.getItem();
                Game.bombLevelUp(1);
                isCollected = true;
            }
        }
        return false;
    }


}
