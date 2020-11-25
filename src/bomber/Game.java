package bomber;

import bomber.entity.*;
import bomber.gameFunction.Map;
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

public class Game extends Application {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 9;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public Map map;


    @Override
    public void start(Stage stage) throws Exception {
        canvas = new Canvas(Texture.IMAGE_SIZE * WIDTH, Texture.IMAGE_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        map = new Map();
        map.loadTile();
        map.setEntityList(entities);
        map.loadMap(System.getProperty("user.dir") + "\\src\\level\\level1.txt");

        stillObjects = map.mapTileArrayToList();
       // Pawn p = new Enemy("needle");

        Player p = new Player();
        p.setMap(map);
        p.setScene(scene);
        p.setXY(1,1);

        entities.add(p);

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
        entities.forEach(e -> e.update());
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public static void main(String[] args) {

        javafx.application.Application.launch(Game.class);


    }

}
