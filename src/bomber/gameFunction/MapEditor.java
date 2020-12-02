package bomber.gameFunction;

import bomber.Game;
import bomber.entity.Bomb;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import java.io.FileWriter;
import java.io.IOException;

public class MapEditor {
    Map map;
    static final String pathToCustom = System.getProperty("user.dir") + "\\src\\level\\custom\\map.txt";
    private int selectedTile = 0;
    private boolean enabled = false;
    private Texture selectedTexture;
    //Custom cursor
    int toX = 0;
    int toY = 0;
    int x;
    int y;
    TimeCounter bombCounter = new TimeCounter();
    double bombRate = 0.1;
    //int toX = x;
    //int toY = y;


    public MapEditor() {
        selectedTexture = new Texture(System.getProperty("user.dir") + "\\src\\texture\\map\\selected.png");
    }

    public int coordinateDown(double axis) {
        return (int) (axis / Texture.IMAGE_SIZE);
    }

    public void update() {
        if (game.input.contains("F12")) {
            enabled = true;
            System.out.println("Map editor: " + (enabled ? "On" : "Off"));
        }
        if (game.input.contains("F11")) {
            enabled = false;
            System.out.println("Map editor: " + (enabled ? "On" : "Off"));
        }
        if (enabled) {

            map.tileId[selectedTile].render(game.getGc(), toX, toY);
            game.getGc().drawImage(selectedTexture.getImage(), toX * Texture.IMAGE_SIZE, toY * Texture.IMAGE_SIZE);
            /*
            if (x == toX && y == toY) {
                if (game.input.contains("I")) {
                    toY--;
                } else if (game.input.contains("L")) {
                    toX++;
                } else if (game.input.contains("J")) {
                    toX--;
                } else if (game.input.contains("K")) {
                    toY++;
                }
            }*/
            toX = coordinateDown(game.mouseX);
            toY = coordinateDown(game.mouseY);
            if (toX < 0) {
                toX = 0;
            }
            if (toY < 0) {
                toY = 0;
            }
            if (toX >= map.MAP_WIDTH) {
                toX = map.MAP_WIDTH - 1;
            }
            if (toY >= map.MAP_HEIGHT) {
                toY = map.MAP_HEIGHT - 1;
            }
            for (int i = 0; i < Map.TILE_TYPE_LIMIT; i++) {
                if (game.input.contains("DIGIT" + i)) {
                    selectedTile = i;
                }
            }

            x = toX;
            y = toY;

            if (game.input.contains("PRIMARY")) {
                if (map.getTile(y, x).getId() != (selectedTile))
                    Sound.playSound("placeCasual");
                map.changeTile(x, y, selectedTile);
                //game.updateMap();
            }
            if (game.input.contains("SECONDARY")) {
                if (map.destroyTile(x, y)) {
                    Sound.playSound("destroyRough");
                }
                //game.updateMap();
            }
            if (game.input.contains("F10")) {
                exportMap();
            }
            if (game.input.contains("Z")) {
                if (bombCounter.getTime() > bombRate) {
                    game.addEntity(new Bomb(x, y, 1, 2, map,
                            1,1, "explosionBomb",
                            "placeGentle"));
                    bombCounter.resetCounter();
                }
            }

        }
    }

    Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSelectedTile(int selectedTile) {
        if (selectedTile > 9 || selectedTile < 0) {
            selectedTile = 0;
        }
        this.selectedTile = selectedTile;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    //write map to a txt file
    public void exportMap() {
        try {
            FileWriter writer = new FileWriter(pathToCustom);
            for (int i = 0; i < map.MAP_HEIGHT; i++) {
                for (int j = 0; j < map.MAP_WIDTH; j++) {
                    writer.write(map.getTile(i, j).getId() + 48);
                    // System.out.println(map.getTile(i,j).getId());
                }
                writer.write("\n");
            }
            writer.close();


        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /*
        public void dictionaryExportToFile(Dictionary d,String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        for (Word word : d.wordArrayList){
            writer.write(word.getText()+"\t"+ word.getDefinition()+"\n");
        }
        writer.close();
    }

     */
}
