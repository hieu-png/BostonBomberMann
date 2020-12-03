package bomber.entity;

import bomber.gameFunction.Map;
import bomber.gameFunction.Texture;

public abstract class Pawn extends Entity {
    protected String label;
    protected Map mapRef;
    protected double toX = x, toY = y;
    protected double speed = 2 / (double) Texture.IMAGE_SIZE;
    protected double tempSpeedBoost = 0;
    protected int[][] mapInfo;
    protected int mapUpdateRate = 5;
    protected int mapUpdateCounter = 0;

    public void setSpeed(double speed) {
        this.speed = (speed + tempSpeedBoost) / (double) Texture.IMAGE_SIZE;
    }

    public double getTempSpeedBoost() {
        return tempSpeedBoost / (double) Texture.IMAGE_SIZE;
    }

    public void setMapRef(Map mapRef) {
        this.mapRef = mapRef;
        mapInfo = mapRef.createNavigationMap();

    }



    public Pawn() {

    }

    public abstract void start();


    public void updateMapInfo() {
        mapUpdateCounter++;

        if (mapUpdateCounter > mapUpdateRate) {
            mapInfo = mapRef.createNavigationMap();
            mapUpdateCounter = 0;

        }
    }

    public boolean checkIfTileEmpty(double x, double y) {
        return mapInfo[(int) y][(int) x] == 0;
    }

    public Pawn(String northTexture, String eastTexture, String southTexture, String label) {
        super(northTexture, eastTexture, southTexture);
        this.label = label;

    }

    public Pawn(String northTexture, String eastTexture, String southTexture) {
        super(northTexture, eastTexture, southTexture);

    }

    public double getSpeed() {
        return speed + getTempSpeedBoost();
    }

    public double moveDirection(Double currentCoordinate, Double targetCoordinate) {
        double distanceToChange = getSpeed();
        //Smooth distance to change to not pass over
        if (Math.abs(currentCoordinate - targetCoordinate) < distanceToChange) {
            distanceToChange = Math.abs(currentCoordinate - targetCoordinate);
        }
        if (currentCoordinate > targetCoordinate)

            distanceToChange *= -1;

        currentCoordinate += distanceToChange;
        return currentCoordinate;
    }

    public boolean isMoving() {
        if (checkIfTileEmpty(toX, toY)) {
            if (x != toX)
                return true;
            else if (y != toY) {
                return true;
            } else return false;
        } else {
            toY = y;
            toX = x;
            return false;
        }
    }
    public double getToX() {
        return toX;
    }
    public double getToY() {
        return toY;
    }
    public void move() {
        //Move in x direction first, then y
        if (checkIfTileEmpty(toX, toY) && active) {
            x = moveDirection(x, toX);
            double i = Double.compare(x, toX);
            if (i > 0) {
                setDirectionFacing(facingDirection.WEST);
            } else if (i < 0) {
                setDirectionFacing(facingDirection.EAST);
            } else if (i == 0) {
                y = moveDirection(y, toY);
                i = Double.compare(y, toY);
                if (i > 0) {
                    setDirectionFacing(facingDirection.NORTH);
                } else if (i < 0) {
                    setDirectionFacing(facingDirection.SOUTH);
                }
            }
        }
    }


}
