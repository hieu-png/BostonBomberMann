package bomber.entity;

import bomber.gameFunction.Map;
import bomber.gameFunction.Texture;

public abstract class Pawn extends Entity {
    protected String label;
    protected Map map;
    protected double toX = x, toY = y;
    protected double speed = 2 / (double) Texture.IMAGE_SIZE;

    public void setMap(Map map) {
        this.map = map;
    }

    public Pawn() {

    }

    public Pawn(String northTexture, String eastTexture, String southTexture, String label) {
        super(northTexture, eastTexture, southTexture);
        this.label = label;

    }

    public Pawn(String northTexture, String eastTexture, String southTexture) {
        super(northTexture, eastTexture, southTexture);
    }

    public double moveDirection(Double currentCoordinate, Double targetCoordinate) {
        double distanceToChange = speed;
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
        if (map.isTileEmpty(toX, toY)) {
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

    public void move() {
        //Move in x direction first, then y
        if (map.isTileEmpty(toX, toY)) {
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
