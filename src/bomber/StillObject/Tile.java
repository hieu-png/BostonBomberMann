package bomber.StillObject;

import bomber.entity.Entity;
import bomber.gameFunction.Texture;
import javafx.scene.canvas.GraphicsContext;

public class Tile extends Entity {
    private Tile floorTile;
    private int id;

    public Tile() {


    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Tile(String path) {
        super(path);
    }

    public Tile(Tile other, boolean active) {
        this.x = other.x;
        this.y = other.y;
        this.id = other.id;
        this.canBePassed = other.canBePassed;
        this.destructible = other.destructible;
        this.health = other.health;
        this.northTexture = other.northTexture;
        this.floorTile = other.floorTile;
        this.maxHealth = other.maxHealth;


        this.active = active;
        this.destroyOnDeath = true;
    }

    public Tile(Tile other) {
        this.x = other.x;
        this.y = other.y;
        this.id = other.id;
        this.canBePassed = other.canBePassed;
        this.destructible = other.destructible;
        this.health = other.health;
        this.maxHealth = other.maxHealth;
        this.northTexture = other.northTexture;
        this.floorTile = other.floorTile;
        this.destroyOnDeath = true;
    }


    public Tile getFloorTile() {
        return floorTile;
    }

    public void setFloorTile(Tile floorTile) {
        this.floorTile = floorTile;
    }

    public void renderState(GraphicsContext gc, Texture stage1, Texture stage2, Texture stage3) {
        double ratio = (double) health / (double) maxHealth;
        Texture stage = null;
        if (ratio >= 1) {

        } else if (ratio >= 0.66) {
            stage = stage1;
        } else if (ratio >= 0.33) {
            stage = stage2;
        } else {
            stage = stage3;
        }
        if(stage != null)
        gc.drawImage(stage.getImage(), x*Texture.IMAGE_SIZE,y*Texture.IMAGE_SIZE);


    }

    @Override
    public void update() {

    }
}
