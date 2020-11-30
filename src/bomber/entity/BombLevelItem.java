package bomber.entity;

import bomber.Game;
import bomber.gameFunction.Sound;

public class BombLevelItem extends Tile{
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
