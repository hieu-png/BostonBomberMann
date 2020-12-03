package bomber.Item;

import bomber.Game;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.StillObject.Tile;
import bomber.gameFunction.Sound;

public class ItemPlayerHealth extends Item {
    public ItemPlayerHealth(int x, int y) {
        super(Game.textureFolderPath + "itemHealth.png",x,y);
    }

    @Override
    public void doThisWhenCollided(Player player) {
        player.healthUp();
        Sound.getItem();
        Game.HpUp(1);
    }


}
