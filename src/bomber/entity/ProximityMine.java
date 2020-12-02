package bomber.entity;

import bomber.entity.Bomb;
import bomber.entity.Enemy.Enemy;
import bomber.entity.Entity;
import bomber.gameFunction.Map;

import java.util.List;

public class ProximityMine extends Bomb {
    public ProximityMine() {

    }

    public ProximityMine(double x, double y, int range, Map mapRef, String bombSound, String placementSound) {
        super(x, y, range, 99, mapRef, 4, 1, bombSound, placementSound);
        entityList = mapRef.getEntityList();
        this.canBePassed = true;
    }

    List<Entity> entityList;

    @Override
    public void update() {
        entityList.forEach(e -> {
            if (e instanceof Enemy) {
                if (this.distanceTo(e) <= 1) {
                    explode();
                }
            }
        });
    }
}
