package bomber;

import bomber.Item.*;
import bomber.StillObject.Gate;
import bomber.StillObject.Tile;
import bomber.entity.Enemy.Balloon;
import bomber.entity.Enemy.Enemy;
import bomber.entity.Enemy.Needle;
import bomber.entity.Enemy.SkullHead;
import bomber.entity.Entity;
import bomber.entity.Player;
import bomber.gameFunction.InGameUI;
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
    MainMenu mainMenu;


    //-------------------So luong Enemy dang trong map------------------
    public int numOfEnemy = 0;


    private boolean gatePassed = false;

    private static double playerSpeed = 2;
    private static int bombLevel = 0;
    private static int hpPlayer = 1;
    private static double bombNumber = 0;


    public int getNumOfEnemy() {
        return numOfEnemy;
    }


    //---------------------End item,speed,.....---------------------------------
    public static int randomInt(int min, int max) {

        return (int) (Math.random() * (max - min + 1) + min);
    }

    Scene scene;
    InGameUI userInterface;


    private GraphicsContext gc;
    private Player player;


    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Item> items = new ArrayList<>();


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
        gate.setXY(WIDTH, HEIGHT / 2 - 1);
        entities.add(gate);
        me = new MapEditor();
        me.setMap(map);
        me.setGame(this);
        //Player
        player = new Player();
        player.setMapRef(map);
        player.setXY(1, 1);
        player.setInput(input);
        player.setHealth(hpPlayer);
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
        items.add(new ItemPlayerHealth(2, 1));
        items.add(new ItemSpeed(6, 10));
        items.add(new ItemBombRange(11, 4));
        items.add(new ItemBombNumberUp(3, 1));

        entities.addAll(items);
        userInterface = new InGameUI();
        userInterface.setUp(player, me, this);
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
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    public void update() {
        getInput();
        if(!player.getActive()) {
            System.out.println("Player Dead");
            mainMenu.drawPlayAgain();
            //wait(10);
//            mainMenu.playAgain();
        }
        //can not remove or add while in the middle of iterating through list, have to use this;
        player.setHealth(hpPlayer);
        player.setSpeed(playerSpeed);
        player.setBombRangeBonus(bombLevel);
        player.setBombCoolDown(bombNumber);
        for (Entity e : entities) {
            if (e.isToDelete()) {
                removeEntity(e);
            } else if (e instanceof Item) {
                if (((Item) e).collided(player)) {
                    e.destroy();

                    entities.remove(e);
                }
            } else {
                e.update();
            }
        }
        if (gatePassed) {//----------------------------------------------------------------------------------
            //if(getNumOfEnemy()==0) {
            System.out.println("Next Level");
            newGame();
            mainMenu.nextLevel();
            // }

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
        userInterface.render(gc);
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

    public void newGame() {
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        if (!this.entities.isEmpty()) {
            this.entities.clear();
        }
        stillObjects.clear();
        playerSpeed = 2;
        bombLevel = 1;
        hpPlayer = 1;
        bombNumber = 1;
        numOfEnemy = 0;
        player = null;
    }

    public void setGatePassed(boolean gatePassed) {
        this.gatePassed = gatePassed;
    }

    public boolean getGatePassed() {
        return gatePassed;
    }

    public boolean isGatePassed() {
        return gatePassed;

    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void addEnemy(Enemy enemy, int x, int y) {

        enemy.setMapRef(map);
        enemy.setXY(x, y);
        enemy.start();
        addEntity(enemy);
        numOfEnemy++;

    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


    public static double randomDouble(double min, double max) {

        return Math.random() * (max - min + 1) + min;
    }

    public void updateMap() {
        stillObjects = map.mapTileArrayToList();

    }

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
        System.out.print(
                bombNumberUp);
        bombNumber += bombNumberUp;
        System.out.println(" : -> bombNumberUp successful : -> " + bombNumberUp);
    }

    private List<Entity> getEntitiesList() {
        return entities;
    }

    public static void setHpPlayer(int hpPlayer1) {
        hpPlayer = hpPlayer1;
    }
}