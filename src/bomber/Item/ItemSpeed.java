package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class ItemSpeed extends Item {
    public ItemSpeed(int x, int y) {
        super(Game.textureFolderPath + "itemSpeedUp.png",x,y);
    }

    @Override
    public void doThisWhenCollided() {
        Sound.getItem();
        Game.speedUp(1);
    }

}
