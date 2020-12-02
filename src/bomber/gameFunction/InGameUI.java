package bomber.gameFunction;

import bomber.Game;
import bomber.MainMenu;
import bomber.entity.Player;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InGameUI {
    Player player;
    Texture bar;
    Game game;
    MapEditor mapEditor;
    int yStart = Map.MAP_HEIGHT * Texture.IMAGE_SIZE;


    Group root = new Group();
    //Map editor cursor.
    Text meX, meY;
    Font font;

    public void setUp(Player player, MapEditor mapEditor, Game game) {
        this.player = player;
        this.mapEditor = mapEditor;
        this.game = game;

        font = Font.font("Arial", MainMenu.fontSize);
        meX = new Text();
        meY = new Text();
        meX.setFont(font);
        meY.setFont(font);
        meX.setX(Map.MAP_WIDTH - Texture.IMAGE_SIZE);
        meX.setY(yStart);
        meY.setX(Map.MAP_WIDTH - Texture.IMAGE_SIZE);
        meY.setY(yStart + Texture.IMAGE_SIZE);

        root.getChildren().add(meY);
        root.getChildren().add(meX);

    }

    public InGameUI() {
        bar = new Texture(Game.textureFolderPath + "bar.png");

    }

    public void render(GraphicsContext gc) {
        bar.render(gc, 0, yStart);
        gc.drawImage(new Texture(Game.textureFolderPath + "Bomb" + (player.getSelectedBomb() + 1) + ".png").getImage(),
                Texture.IMAGE_SIZE, yStart);

        if (mapEditor.isEnabled()) {
            meX.setText("" + mapEditor.x);
            meY.setText("" + mapEditor.y);
        } else {
            meX.setText("");
            meY.setText("");
        }
    }
}
