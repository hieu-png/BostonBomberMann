package bomber.entity;


import bomber.gameFunction.Texture;

import java.awt.*;

enum passable {
    YES, NO
}


public abstract class Entity {
    public int x;
    public int y;
    protected passable canBePassed;
    public static final int PIXEL_SIZE = 32;
    protected int health = 2;
    public boolean active = false;
    public boolean destructible = true;
    Texture texture;

    public Entity() {

    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        texture.setXY(x, y);
    }

    public void dealDamage(int damage, Entity other) {
        other.takeDamage(damage);
    }

    public boolean takeDamage(int damage) {
        if (destructible) {
            health -= damage;
            if(health <= 0) {
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

    public abstract void Update();
}
