package bomber.gameFunction;

import bomber.Game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Application {

    //public static final int WIDTH = 16;
    //public static final int HEIGHT = 9;
    protected final double fontSize = 36;

    private int _level;
    private GraphicsContext gc;
    private Game game;
    private Group root;
    double x,y;

    @Override
    public void start(Stage stage) {

        game = new Game(Texture.IMAGE_SIZE * Game.WIDTH, Texture.IMAGE_SIZE * Game.HEIGHT);

        gc = game.getGraphicsContext2D();
        Scene scene;

        _level = 1;
        root = new Group();
        x = game.getWidth();
        y = game.getHeight();
        setBackGround();


//----------------------render Function---------------------------------
        Text startText = new Text();
        startText.setText("Play");
        startText.setFont(Font.font("Arial", fontSize));
        System.out.print(startText.getFont());
        startText.setX((x - fontSize * 3.2) / 2);
        startText.setY(y / 3);
        startText.setFill(Color.RED);
        root.getChildren().add(startText);
        Text levelText = new Text("Level");
        levelText.setFont(Font.font("Arial", fontSize));
        levelText.setX((x - fontSize * 3.2) / 2);
        levelText.setY(y / 3 + 40);
        levelText.setFill(Color.BLUE);
        root.getChildren().add(levelText);
//-------------------End render Function--------------------------------

        stage.setTitle("Bomberman");
        stage.setResizable(false);
        scene = new Scene(root);
        scene.setFill(Color.LIGHTGRAY);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        game.setScene(scene);
//--------------------Event--------------------------------------------
        startText.setOnMouseClicked(mouseEvent -> {
            root.getChildren().clear();
            root.getChildren().add(game);
            game.start(scene, _level);
        });
        levelText.setOnMouseClicked(mouseEvent -> {
            root.getChildren().clear();
            setBackGround();
            int levels = 2;
            List<Text> texts = new ArrayList<>(levels);
            for (int i = 0; i < levels; i++) {
                Text te = new Text("Level " + (i + 1));
                texts.add(te);
                te.setFont(Font.font("Arial", 20));
                te.setX(20 + 80 * i);
                te.setY(30);//can nang cap
                int finalI = i;
                te.setOnMouseClicked(mouseEvent1 -> {
                    set_level(finalI + 1);
                    for(Text c : texts) {
                        c.setFill(Color.BLACK);
                    }
                    te.setFill(Color.WHITESMOKE);
                });
            }
            root.getChildren().addAll(texts);
            Text back = new Text("Back");
            back.setFont(Font.font("Arial", 20));
            back.setFill(Color.RED);
            back.setX(20);
            back.setY(game.getHeight() - 30);
            root.getChildren().add(back);
            back.setOnMouseClicked(mouseEvent1 -> {
                root.getChildren().clear();
                setBackGround();
                root.getChildren().add(levelText);
                root.getChildren().add(startText);

            });

        });
//-------------------End event------------------------------------------
        if(game.playAgain()) {
            drawPlayAgain();
        }
    }
    public void drawPlayAgain() {
        Rectangle rec = new Rectangle(5*Texture.IMAGE_SIZE,5*Texture.IMAGE_SIZE,
                6*Texture.IMAGE_SIZE,3*Texture.IMAGE_SIZE);
        rec.setFill(Color.LIGHTGRAY);
        root.getChildren().add(rec);
        Text playAgain = new Text("Play again");
        playAgain.setX(5*Texture.IMAGE_SIZE + 10);
        playAgain.setY(4*Texture.IMAGE_SIZE);
        playAgain.setFont(new Font("Arial",20));
        root.getChildren().add(playAgain);
        playAgain.setOnMouseClicked(mouseEvent -> {
            playAgain();
        });
    }

    public void setBackGround() {
        try {
            Image image = new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\texture\\mainmenu.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(y);
            imageView.setFitWidth(x);
            root.getChildren().addAll(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playAgain() {
        root.getChildren().clear();
        root.getChildren().add(game);
    }

    public void nextLevel() {
        _level++;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public static void main(String[] args) {
        Sound.ThemeSound();
        javafx.application.Application.launch(MainMenu.class);


    }
}
