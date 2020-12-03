package bomber.entity;


import bomber.gameFunction.Texture;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public abstract class Entity {
    protected double x = 0;
    protected double y = 0;
    protected boolean canBePassed = false;
    protected int health = 1;
    protected int maxHealth = 1;
    protected boolean active = true;
    protected boolean destructible = true;
    protected boolean toDelete = false;
    public boolean destroyOnDeath = false;
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    protected facingDirection directionFacing = facingDirection.NORTH;
    protected Texture northTexture;
    protected Texture eastTexture;
    protected Texture southTexture;
    protected Texture allTexture;

    public Texture getNorthTexture() {
        return northTexture;
    }

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
    public Entity(String northTexture, String eastTexture, String southTexture, String allTexture) {
        this.allTexture = new Texture(allTexture);
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

    public double distanceTo(Entity other) {
        return Math.sqrt(Math.pow(x - other.getX(), 2) +
                         Math.pow(y - other.getY(), 2));
    }
    public double distanceTo(double x, double y) {
        return Math.sqrt(Math.pow(x - this.getX(), 2) +
                Math.pow(y - this.getY(), 2));
    }

    public static double distanceTo(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) +
                Math.pow(y1 - y2, 2));
    }

    public boolean isCollidedWith(Entity other) {
            return distanceTo(other) < 0.5;
    }

    public void render(GraphicsContext gc, int x, int y) {
        Image img = null;
        int flip = 1;//-1 to flip
        int xOffset = 0;
        switch (directionFacing) {
            case ALL -> img = allTexture.getImage();
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
                flip * Texture.IMAGE_SIZE * size, Texture.IMAGE_SIZE * size);
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
                case ALL -> {
                    img = allTexture.getImage();
                }
            }
            int size = 1;

            //ImageView iv = new ImageView(img);
            //Image base = iv.snapshot(params, null);

            gc.drawImage(img, 0, 0,
                    img.getWidth() * size, img.getHeight() * size,
                    (x + xOffset) * Texture.IMAGE_SIZE * size, y * Texture.IMAGE_SIZE * size,
                    flip * Texture.IMAGE_SIZE * size, Texture.IMAGE_SIZE * size);


            // gc.drawImage(img,x*Texture.IMAGE_SIZE*size,y*Texture.IMAGE_SIZE*size);
        }
    }

    public boolean dealDamage(int damage, Entity other) {
        return other.takeDamage(damage);
    }
    public void onTakeDamage() {

    }
    public boolean takeDamage(int damage) {
        if (destructible) {
            health -= damage;
            if (health <= 0)
            {
                if(destroyOnDeath)
                    destroy();
                    else
                deactivate();
            } else
                onTakeDamage();

            return true;
        } else {
            return false;
        }
    }


    public void spawn() {
        active = true;
    }
    //call when delete
    public void destroy() {
        toDelete = true;
    }
    //activation, still can be reactivated
    public void deactivate() {
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
        maxHealth = health;
        this.health = health;
    }

    public boolean getActive() {
        return active;
    }

}
