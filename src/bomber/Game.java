package bomber;

import bomber.Item.HpPlayerItem;
import bomber.Item.Item;
import bomber.Item.SpeedItem;
import bomber.StillObject.Gate;
import bomber.StillObject.Tile;
import bomber.entity.Enemy.Enemy;
import bomber.entity.Enemy.Needle;
import bomber.entity.Enemy.SkullHead;
import bomber.entity.Enemy.Balloon;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.gameFunction.Map;
import bomber.gameFunction.MapEditor;
import bomber.gameFunction.Texture;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game extends Canvas {
    public static final String textureFolderPath = System.getProperty("user.dir") + "\\src\\texture\\";
    public static final int WIDTH = 25;
    public static final int HEIGHT = 12;
    public double mouseX = 0;
    public double mouseY = 0;
    public MainMenu mainMenu = new MainMenu();

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    //-------------------So luong Enemy dang trong map------------------
    public int numOfEnemy = 0;

    public int getNumOfEnemy() {
        return numOfEnemy;
    }

    //-------------------------------End so luong enemy---------------------

    //-------------------BombLevel,BombNumber,Speed,HpPlayer, and function-------------------------------------
    private static double playerSpeed = 2;
    private static double bombLevel = 1;
    private static int hpPlayer = 1;
    private static double bombNumber = 1;

    public static void speedUp(double speed) {
        System.out.print(playerSpeed);
        playerSpeed += speed;
        System.out.println(" : -> Speed up successful : -> " + playerSpeed);
    }

    public static void bombLevelUp(double bomblevel) {
        System.out.print(playerSpeed);
        bombLevel += bomblevel;
        System.out.println(" : -> bombLevelUp successful : -> " + bomblevel);

    }

    public static void HpUp(double Hp) {
        System.out.print(hpPlayer);
        hpPlayer += Hp;
        System.out.println(" : -> HpUp successful : -> " + hpPlayer);
    }

    public static void bombNumberUp(double bombNumberUp) {
        System.out.print(bombNumberUp);
        bombNumber += bombNumberUp;
        System.out.println(" : -> bombNumberUp successful : -> " + bombNumberUp);
    }
//---------------------End item,speed,.....---------------------------------


    public static int randomInt(int min, int max) {

        return (int) (Math.random() * (max - min + 1) + min);
    }

    Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }


    public static double randomDouble(double min, double max) {

        return Math.random() * (max - min + 1) + min;
    }

    private GraphicsContext gc;
    private Player player;

    public GraphicsContext getGc() {
        return gc;
    }

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    private List<Entity> getEntitiesList() {
        return entities;
    }


    public void addEnemy(Enemy enemy, int x, int y) {

        enemy.setMapRef(map);
        enemy.setXY(x, y);
        enemy.start();
        addEntity(enemy);
        numOfEnemy++;

    }

    Stack<Entity> addStack = new Stack<>();

    public void addEntity(Entity e) {
        addStack.push(e);
    }

    Stack<Entity> removeStack = new Stack<>();

    public void removeEntity(Entity e) {
        removeStack.push(e);
    }

    public Game(double width, double height) {
        super(width, height);
    }

    public void updateMap() {
        stillObjects = map.mapTileArrayToList();

    }

    public ArrayList<String> input = new ArrayList<>();

    public Map map;
    MapEditor me;

    Texture crackStage1;
    Texture crackStage2;
    Texture crackStage3;

    public void start(int level) {
        gc = this.getGraphicsContext2D();
        crackStage1 = new Texture(textureFolderPath + "crack1.png");
        crackStage2 = new Texture(textureFolderPath + "crack2.png");
        crackStage3 = new Texture(textureFolderPath + "crack3.png");

        map = new Map();
        map.setGame(this);

        map.loadTile();
        map.setEntityList(entities);
        map.loadMap(System.getProperty("user.dir") + "\\src\\level\\level" + level + ".txt");
        updateMap();
        Gate gate = new Gate(this);
        gate.setXY(this.WIDTH - 1, this.HEIGHT / 2 + 1);
        entities.add(gate);
        me = new MapEditor();
        me.setMap(map);
        me.setGame(this);
        //Player
        player = new Player();
        player.setMapRef(map);
        player.setXY(1, 1);
        player.setInput(input);

        entities.add(player);
//-------------------------Enemy-----------------------
        Needle b = new Needle();
        b.setPlayer(player);
        addEnemy(b, 13, 12);

        SkullHead oneal = new SkullHead();
        oneal.setPlayer(player);
        addEnemy(oneal, 14, 12);

        Balloon throughtWall = new Balloon();
        throughtWall.setPlayer(player);
        addEnemy(throughtWall, 14, 12);
//------------------------End Enemy--------------------------------------------------------------------

//-----------------------Item-----------------------------------------------------------------------------------------
        items.add(new ItemPlayerHealth(1, 2));
        items.add(new ItemSpeed(1, 3));
        //items.add(new BombNumberUpItem(2, 1));
        //items.add(new BombLevelItem(3, 1));

        entities.addAll(items);
//----------------------End Item---------------------------------------------------------------------------------------
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();

                update();
            }
        };
        timer.start();

    }

    //    private static double playerSpeed = 2;
//    private static double bombLevel = 1;
//    private static double hpPlayer = 1;
//    private static double bombNumber = 1;
    public void update() {
        getInput();
        //can not remove or add while in the middle of iterating through list, have to use this;
        player.setHealth(hpPlayer);
        player.setSpeed(playerSpeed);
        for (Entity e : entities) {
            if (e.isToDelete()) {
                removeEntity(e);
            } else if (e instanceof Item) {
                if (((Item) e).collided(player)) {
                    e.destroy();


                    //set bomb range here

                    //set bomb NUmber here

                    entities.remove(e);
                }
            } else if (e instanceof Gate) {
                if(((Gate) e).collide(player)){
                    System.out.println("Next level");
                }
            } else {
                e.update();
            }

        }
        while (!addStack.isEmpty()) {
            entities.add(addStack.pop());
        }
        while (!removeStack.isEmpty()) {
            entities.remove(removeStack.pop());
        }


        me.update();

        updateMap();

    }

    public void render() {
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        stillObjects.forEach(g -> {
            g.render(gc);
            ((Tile) g).renderState(gc, crackStage1, crackStage2, crackStage3);
        });
        entities.forEach(g -> g.render(gc));
    }

    public boolean isplayAgain() {
        for (Entity e : entities) {
            if (e instanceof Player) {
                if (!e.getActive()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void getInput() {
        scene.setOnMouseClicked(mouseEvent -> {
            //mouseEvent.getButton()


        });
        scene.setOnMousePressed(mouseEvent -> {
            String code = mouseEvent.getButton().toString();

            if (!input.contains(code))
                input.add(code);
        });
        scene.setOnMouseReleased(mouseEvent -> {
            String code = mouseEvent.getButton().toString();
            input.remove(code);
        });
        scene.setOnMouseMoved(mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        });
        scene.setOnKeyPressed(keyEvent -> {
            String code = keyEvent.getCode().toString();
            //System.out.println("Input detected: " + code);

            if (!input.contains(code))
                input.add(code);
        });
        scene.setOnKeyReleased(keyEvent -> {
            String code = keyEvent.getCode().toString();
            //System.out.println("Input released: " + code);
            input.remove(code);
        });
    }

}
