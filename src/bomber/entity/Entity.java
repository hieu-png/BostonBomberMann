package bomber.entity;


import bomber.gameFunction.Texture;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;


public abstract class Entity {
    protected double x = 0;
    protected double y = 0;
    protected boolean canBePassed = false;
    protected int health = 1;
    protected boolean active = true;
    protected boolean destructible = true;
    protected facingDirection directionFacing = facingDirection.NORTH;
    protected Texture northTexture;
    protected Texture eastTexture;
    protected Texture southTexture;

    public Entity() {

    }

    public Entity(String texturePath) {
        northTexture = new Texture(texturePath);
        directionFacing = facingDirection.STATIONARY;
    }

    public Entity(String northTexture, String eastTexture, String southTexture) {
        this.northTexture = new Texture(northTexture);
        this.eastTexture = new Texture(eastTexture);
        this.southTexture = new Texture(southTexture);
    }


    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String getXY() {
        return "(" + x + "/" + y + ")";
    }

    public void setDirectionFacing(facingDirection directionFacing) {
        if (this.directionFacing != facingDirection.STATIONARY)
            this.directionFacing = directionFacing;
    }

    public boolean isCollidedWith(Entity other) {
        double b = Math.sqrt(Math.pow(x - other.getX(), 2) - Math.pow(y - other.getY(), 2));
        return b < 32;
    }


    public void render(GraphicsContext gc) {
        if (active) {
            //SnapshotParameters params = new SnapshotParameters();
            //params.setFill(Color.TEAL);

            Image img = null;
            int flip = 1;//-1 to flip
            int xOffset = 0;
            switch (directionFacing) {
                case STATIONARY, NORTH -> img = northTexture.getImage();
                case SOUTH -> img = southTexture.getImage();
                case EAST -> img = eastTexture.getImage();
                case WEST -> {
                    img = eastTexture.getImage();
                    flip = -1;
                    xOffset = 1;
                }
            }
            int size = 1;

            //ImageView iv = new ImageView(img);
            //Image base = iv.snapshot(params, null);

            gc.drawImage(img, 0, 0,
                    img.getWidth() * size, img.getHeight() * size,
                    (x + xOffset) * Texture.IMAGE_SIZE * size, y * Texture.IMAGE_SIZE * size,
                    flip * img.getWidth() * size, img.getHeight() * size);


            // gc.drawImage(img,x*Texture.IMAGE_SIZE*size,y*Texture.IMAGE_SIZE*size);
        }
    }

    public void dealDamage(int damage, Entity other) {
        other.takeDamage(damage);
    }

    public boolean takeDamage(int damage) {
        if (destructible) {
            health -= damage;
            if (health <= 0) {
                destroy();
            }
            return true;
        } else {
            return false;
        }
    }


    public void spawn() {
        active = true;
    }

    public void destroy() {
        active = false;
    }

    // public abstract void Start();
    public abstract void update();


    public boolean isDestructible() {
        return destructible;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public int getHealth() {
        return health;
    }

    public boolean getCanBePassed() {
        return canBePassed;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCanBePassed(boolean canBePassed) {
        this.canBePassed = canBePassed;
    }

    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }

    public void setHealth(int health) {
        this.health = health;
    }


}
