package bomber.Item;

import bomber.Game;
import bomber.entity.Player;
import bomber.gameFunction.Sound;

public class ItemBombCoolDown extends Item {

    public ItemBombCoolDown(int x, int y) {
        super(Game.textureFolderPath + "itemCircle.png", x, y);
    }

    @Override
    public void doThisWhenCollided(Player player) {
        Sound.getItem();
        Game.bombCoolDown(1);
    }


}
