package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class ItemBombRange extends Item {
    public ItemBombRange(int x, int y) {
        super(Game.textureFolderPath + "itemBombRange.png",x,y);

    }

    @Override
    public void doThisWhenCollided() {
        Sound.getItem();
        Game.bombLevelUp(1);
    }
}
