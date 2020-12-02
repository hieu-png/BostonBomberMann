package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class ItemBombNumberUp extends Item {

    public ItemBombNumberUp(int x, int y) {
        super(Game.textureFolderPath + "itemCircle.png", x, y);
    }

    @Override
    public void doThisWhenCollided() {
        Sound.getItem();
        Game.bombNumberUp(1);
    }


}
