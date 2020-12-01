package bomber.entity;

import bomber.Game;
import bomber.gameFunction.Map;
import bomber.gameFunction.TimeCounter;


public class ExplosionBlast extends Entity {
    Map mapRef;
    int range = 2;

    public double delayDuration = 0;
    private TimeCounter delayCounter;
    boolean delaying = true;
    public double lingerDuration = 0.15 + delayDuration;
    private TimeCounter lingerDurationCounter;

    public ExplosionBlast() {

    }

    public ExplosionBlast(double x, double y, int range, Map map,
                          facingDirection _facingDirection, double explosionDelay) {
        super(Game.textureFolderPath + "flameNorth.png",
                Game.textureFolderPath + "flameEast.png",
                Game.textureFolderPath + "flameSouth.png",
                Game.textureFolderPath + "flameAll.png");
        lingerDurationCounter = new TimeCounter();
        delayCounter = new TimeCounter();
        this.x = x;
        this.y = y;
        this.range = range;
        this.mapRef = map;
        delayDuration = explosionDelay;
        this.directionFacing = _facingDirection;
        this.destructible = false;
        this.setCanBePassed(true);
        if (mapRef.destroyTile(x, y)) {
            this.range = 0;
        }
        for (Entity e : mapRef.getEntityList()) {
            if (e.isCollidedWith(this)) {
                e.takeDamage(1);
            }
        }
        mapRef.getGame().addEntity(this);

    }


    public void traverse(facingDirection fd) {
        double delayOffset = 0.04;
        switch (fd) {
            case ALL -> {
                traverse(facingDirection.NORTH);
                traverse(facingDirection.SOUTH);

                traverse(facingDirection.EAST);
                traverse(facingDirection.WEST);
            }
            case SOUTH -> {
                if (mapRef.isTileDestructible(x, y + 1) || mapRef.isTileEmpty(x, y + 1)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x, y + 1, range - 1, mapRef, fd, delayDuration + delayOffset));
                }
            }
            case NORTH -> {
                if (mapRef.isTileDestructible(x, y - 1) || mapRef.isTileEmpty(x, y - 1)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x, y - 1, range - 1, mapRef, fd, delayDuration + delayOffset));
                }
            }
            case WEST -> {
                if (mapRef.isTileDestructible(x - 1, y) || mapRef.isTileEmpty(x - 1, y)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x - 1, y, range - 1, mapRef, fd, delayDuration + delayOffset));
                }
            }
            case EAST -> {
                if (mapRef.isTileDestructible(x + 1, y) || mapRef.isTileEmpty(x + 1, y)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x + 1, y, range - 1, mapRef, fd, delayDuration + delayOffset));
                }
            }
            case STATIONARY -> {

            }
        }
    }

    @Override
    public void update() {
        if (range > 0) {
            if (delayCounter.getTime() > delayDuration && delaying) {
                traverse(directionFacing);
                delaying = false;

            }
        }
        if (lingerDurationCounter.getTime() > lingerDuration) {
            destroy();
        }
    }
}
