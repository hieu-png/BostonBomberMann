package bomber.entity;

import bomber.Game;
import bomber.gameFunction.Sound;

public class SpeedItem extends Tile{
    private boolean isCollected = false;
    public SpeedItem(String pathOfImage) {
        super(pathOfImage);
    }

    @Override
    public boolean isCollidedWith(Entity other) {
        if(other instanceof Player) {
            if(!isCollected) {
                Sound.getItem();
                Game.speedUp(1);
                isCollected = true;
            }
        }
        return false;
    }


}
