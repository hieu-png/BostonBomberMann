package bomber.entity;

import bomber.Game;
import bomber.gameFunction.Map;
import bomber.gameFunction.Sound;
import bomber.gameFunction.TimeCounter;

public class Bomb extends Entity {
    Map mapRef;
    boolean exploded = false;
    String bombSound;

    void changeStat() {
        this.destructible = false;
        this.canBePassed = false;
        this.timeCounter = new TimeCounter();
    }

    private int penetration = 1;

    public Bomb() {
        changeStat();
    }

    public Bomb(double x, double y, int range, double fuseTime, Map mapRef,
                int bombType, int penetration,String bombSound, String placementSound) {

        super(Game.textureFolderPath + "Bomb" + bombType + ".png");
        this.x = x;
        this.y = y;
        this.mapRef = mapRef;
        this.range = range;
        this.fuseTime = fuseTime;
        this.bombSound = bombSound;
        this.penetration = penetration;
        Sound.playSound(placementSound);
        changeStat();
    }

    TimeCounter timeCounter;
    int range = 1;
    double fuseTime = 2f;

    public void explode() {
        if (!exploded) {
            exploded = true;
            Sound.playSound(bombSound);
            mapRef.getGame().addEntity(new ExplosionBlast(x, y, range, mapRef,
                    facingDirection.ALL, 0, penetration));
            setToDelete(true);
        }
    }

    @Override
    public void update() {
        if (timeCounter.getTime() > fuseTime) {
            explode();
        }

    }
}
