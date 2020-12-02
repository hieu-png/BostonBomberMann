package bomber.entity;

import bomber.Game;
import bomber.gameFunction.Map;
import bomber.gameFunction.Sound;
import bomber.gameFunction.TimeCounter;

public class Bomb extends Entity {
    Map mapRef;
    boolean exploded = false;
    int bombPenetration = 1;
    String bombSound;

    void changeStat() {
        this.destructible = false;
        this.canBePassed = false;
        this.timeCounter = new TimeCounter();
    }

    public Bomb() {
        changeStat();
    }

    public Bomb(double x, double y, int range, double fuseTime, Map mapRef,
                int bombType, int bombPenetration, String bombSound) {

        super(Game.textureFolderPath + "Bomb" + bombType + ".png");
        this.bombPenetration = bombPenetration;
        this.x = x;
        this.y = y;
        this.mapRef = mapRef;
        this.range = range;
        this.fuseTime = fuseTime;
        this.bombSound = bombSound;
        Sound.playSound("beepSmall");
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
                    facingDirection.ALL, 0, bombPenetration));
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
