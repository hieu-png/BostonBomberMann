package bomber;

import bomber.entity.*;
import bomber.gameFunction.Map;
import bomber.gameFunction.MapEditor;
import bomber.gameFunction.Texture;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 12;
    public double mouseX = 0;
    public double mouseY = 0;
//-------------------BombLevel,BombNumber,Speed,HpPlayer, and function-------------------------------------
    private static double playerSpeed = 2;
    private static double bombLevel = 1;
    private static double hpPlayer = 1;
    private static double bombNumber = 1;

    public static void speedUp(double speed) {
        playerSpeed += speed;
    }
    public static void bombLevelUp(double bomblevel) {
        bombLevel += bomblevel;
    }
    public static void HpUp(double Hp) {
        hpPlayer += Hp;
    }
    public static void bombNumberUp(double bombNumberUp) {
        bombNumber += bombNumberUp;
    }
//---------------------End item,speed,.....---------------------------------


    public static int randomInt(int min, int max) {

        return (int) (Math.random() * (max - min + 1) + min);
    }

    Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ArrayList<String> input = new ArrayList<>();

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


    public Map map;
    MapEditor me;

    public void addEnemy(Enemy enemy, int x, int y) {

        enemy.setMap(map);
        enemy.setXY(x, y);
        enemy.start();
        entities.add(enemy);

    }

    public Game(double width, double height) {
        super(width, height);
    }

    public void updateMap() {
        stillObjects = map.mapTileArrayToList();

    }
    public void start(int level) {
        gc = this.getGraphicsContext2D();

        map = new Map();
        map.loadTile();
        map.setEntityList(entities);
        map.loadMap(System.getProperty("user.dir") + "\\src\\level\\level" + level + ".txt");
            updateMap();

        me = new MapEditor();
        me.setMap(map);
        me.setGame(this);
        //Player
         player = new Player();
        player.setMap(map);
        player.setXY(1, 1);
        player.setInput(input);

        entities.add(player);

        Needle b = new Needle();
        b.setPlayer(player);
        addEnemy(b, 8, 6);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

    }

    public void update() {
        getInput();
        for (Entity x : entities) {

            if (!x.getActive()) {
                entities.remove(x);
            } else {
                x.update();
            }
        }
        me.update();

    }

    public void render() {
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public boolean playAgain() {
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
            mouseX  = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            mouseX  = mouseEvent.getX();
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
