package bomber.entity;

import bomber.gameFunction.Map;

public class GasolineBarrel extends Bomb {
    public GasolineBarrel() {

    }

    public GasolineBarrel(double x, double y, int range, Map mapRef, String bombSound, String placementSound) {
        //3 is gasolineTexture
        //Only explode when on fire
        super(x, y, range, 999, mapRef, 3,1, bombSound, placementSound);
    }
}
