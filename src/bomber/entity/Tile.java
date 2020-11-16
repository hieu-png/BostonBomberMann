package bomber.entity;

public class Tile extends Entity {
    public Tile() {


    }
    public Tile(Tile other) {
        this.x = other.x;
        this.y = other.y;
        this.canBePassed = other.canBePassed;
        this.destructible = other.destructible;
        this.health = other.health;
    }

    @Override
    public void Update() {

    }
}
