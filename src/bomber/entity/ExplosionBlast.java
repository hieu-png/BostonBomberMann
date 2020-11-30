package bomber.entity;

import bomber.gameFunction.Map;
enum DIRECTION {
    UP,DOWN,LEFT,RIGHT,ALL
}
public class ExplosionBlast extends Entity {
    Map map;
    int range = 2;
    public void traverse(DIRECTION direction, int range) {
        Tile tile =  map.getTile((int)y,(int)x);
        if(tile.destructible) {
            tile.destroy();
            traverse(direction, range - 1);
        }
    }
    @Override
    public void update() {

    }
}
