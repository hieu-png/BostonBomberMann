package bomber.entity;

import bomber.Game;
import bomber.StillObject.Tile;
import bomber.gameFunction.Map;
import bomber.gameFunction.TimeCounter;

import java.util.ArrayList;
import java.util.List;

public class ExplosionBlast extends Entity {
    Map mapRef;
    int range = 2;
    public double lingerDuration = 0.2;
    private TimeCounter lingerDurationCounter;

    public ExplosionBlast() {

    }

    public ExplosionBlast(double x, double y, int range, Map map, facingDirection _facingDirection) {
        super(Game.textureFolderPath + "flameNorth.png",
                Game.textureFolderPath + "flameEast.png",
                Game.textureFolderPath + "flameSouth.png",
                Game.textureFolderPath + "flameAll.png");
        lingerDurationCounter = new TimeCounter();
        this.x = x;
        this.y = y;
        this.range = range;
        this.mapRef = map;
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
        if (this.range > 0) {



            traverse(directionFacing);
        }
    }


    public void traverse(facingDirection fd) {
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
                            new ExplosionBlast(x, y + 1, range - 1, mapRef, fd));
                }
            }
            case NORTH -> {
                if (mapRef.isTileDestructible(x, y - 1) || mapRef.isTileEmpty(x, y - 1)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x, y - 1, range - 1, mapRef, fd));
                }
            }
            case WEST -> {
                if (mapRef.isTileDestructible(x - 1, y) || mapRef.isTileEmpty(x - 1, y)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x - 1, y, range - 1, mapRef, fd));
                }
            }
            case EAST -> {
                if (mapRef.isTileDestructible(x + 1, y) || mapRef.isTileEmpty(x + 1, y)) {
                    mapRef.getGame().addEntity(
                            new ExplosionBlast(x + 1, y, range - 1, mapRef, fd));
                }
            }
            case STATIONARY -> {

            }
        }
    }

    @Override
    public void update() {
        if (lingerDurationCounter.getTime() > lingerDuration) {
            destroy();
        }
    }
}
