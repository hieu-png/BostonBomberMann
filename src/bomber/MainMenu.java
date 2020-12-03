package bomber;

import bomber.gameFunction.Map;
import bomber.gameFunction.Sound;
import bomber.gameFunction.Texture;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Application {

    //    public static final int WIDTH = 16;
//    public static final int HEIGHT = 9;
    public static final double fontSize = 36;

    private int chosenLevel;
    private final int numberOfLevel = 5;
    private GraphicsContext gc;
    private Game game;
    private Group root;
    double x, y;

    public Game getGame() {
        return game;
    }

    @Override
    public void start(Stage stage) {

        game = new Game(Texture.IMAGE_SIZE * Map.MAP_WIDTH, Texture.IMAGE_SIZE * (Map.MAP_HEIGHT + 2));
        gc = game.getGraphicsContext2D();

        chosenLevel = 1;
        root = new Group();
        x = game.getWidth();
        y = game.getHeight();
        setBackGround();
        game.setMainMenu(this);

        Text startText = new Text();
        startText.setText("Play");
        startText.setFont(Font.font("Arial", fontSize));
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

        stage.setTitle("Bomberman");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        scene.setFill(Color.LIGHTGRAY);

        stage.setScene(scene);
        game.setScene(scene);
        stage.show();

//--------------------Event--------------------------------------------
        startText.setOnMouseClicked(mouseEvent -> {
            startText.setFill(Color.WHITESMOKE);
            root.getChildren().clear();
            root.getChildren().add(game);
            game.start(chosenLevel);
        });
        levelText.setOnMouseClicked(mouseEvent -> {
            levelText.setFill(Color.WHITESMOKE);
            root.getChildren().clear();
            setBackGround();
            List<Text> texts = new ArrayList<>(numberOfLevel);
            for (int i = 0; i < numberOfLevel; i++) {
                Text te = new Text("Level " + (i + 1));
                texts.add(te);
                te.setFont(Font.font("Arial", 20));
                te.setX(20 + 80 * i);
                te.setY(30);//can nang cap
                int finalI = i;
                te.setOnMouseClicked(mouseEvent1 -> {
                    setChosenLevel(finalI + 1);
                    for (Text c : texts) {
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
                back.setFill(Color.WHITESMOKE);
                root.getChildren().clear();
                setBackGround();
                root.getChildren().add(levelText);
                levelText.setFill(Color.RED);
                root.getChildren().add(startText);

            });

        });
//-------------------End event------------------------------------------
        if (game.isPlayAgain()) {
            drawPlayAgain();
        }
    }

    public void addText() {
        Text healthText;
        Text coolDownText;
    }

    public void drawPlayAgain() {
        int x1, y1;
        x1 = (Game.WIDTH - 2) / 2;
        y1 = (Game.HEIGHT) / 2;
        Rectangle rec = new Rectangle(x1 * Texture.IMAGE_SIZE, y1 * Texture.IMAGE_SIZE,
                6 * Texture.IMAGE_SIZE, 3 * Texture.IMAGE_SIZE);
        rec.setFill(Color.LIGHTGRAY);
        root.getChildren().add(rec);
        Text playAgain = new Text("Play again");
        playAgain.setX((x1) * Texture.IMAGE_SIZE + 50);
        playAgain.setY((y1) * Texture.IMAGE_SIZE + 50);
        playAgain.setFont(new Font("Arial", 20));
        root.getChildren().add(playAgain);
        playAgain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode().equals(KeyCode.ENTER)) {
                    playAgain.setFill(Color.WHITESMOKE);
                    System.out.println("Da nhan su kien");
                    root.getChildren().clear();
                    root.getChildren().add(game);
                    game.newGame(true);
                    game.newLevel(chosenLevel);
                }

            }

        });
        playAgain.setOnMouseClicked(mouseEvent -> {
            playAgain.setFill(Color.WHITESMOKE);
            System.out.println("Da nhan su kien");
            root.getChildren().clear();
            root.getChildren().add(game);
            game.newGame(true);
            game.newLevel(chosenLevel);
        });
    }

    public void setBackGround() {
        try {
            Image image = new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\texture\\mainMenu.png"));
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
        System.out.println("nextLevel called");
        if (chosenLevel < numberOfLevel) chosenLevel++;
        else {
            System.out.println("Het level roi");
            System.exit(0);
        }
        root.getChildren().clear();
        root.getChildren().add(game);
        game.newLevel(chosenLevel);
    }

    public void setChosenLevel(int chosenLevel) {
        this.chosenLevel = chosenLevel;
    }

    public static void main(String[] args) {

        Sound.ThemeSound();
        javafx.application.Application.launch(MainMenu.class);


    }
}
