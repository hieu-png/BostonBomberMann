package bomber.entity;


import bomber.gameFunction.Texture;

import java.awt.*;

enum passable {
    YES, NO
}


public abstract class Entity {
    protected int x;
    protected int y;
    protected boolean canBePassed;
    protected int health = 1;
    protected boolean active = true;
    protected boolean destructible = true;
    Texture texture;

    public Entity() {

    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        if(texture != null)
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


    public boolean isDestructible() {
        return destructible;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHealth() {
        return health;
    }

    public boolean getCanBePassed() {
        return canBePassed;
    }

    public Texture getTexture() {
        return texture;
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

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}
