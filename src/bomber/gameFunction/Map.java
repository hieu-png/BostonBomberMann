package bomber.gameFunction;

import bomber.entity.Entity;
import bomber.entity.Tile;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

public class Map {
    //0-9 TileLimit
    public final int MAP_SIZE = 16;

    public final int TILE_TYPE_LIMIT = 10;
    public Tile[][] mapTile = new Tile[MAP_SIZE][MAP_SIZE];
    public Tile[] tileId = new Tile[10];

    /**
     * @param path
     */
    public void loadTile(String path) {

        for (int i = 0; i < TILE_TYPE_LIMIT; i++) {
            //Tile[i] = new Tile()
            String s = String.format(System.getProperty("user.dir") +
                    "\\texture\\map\\%d.png", i);
        }

    }

    public void loadMap(String path) {
        try {
            FileReader f = new FileReader(path);
            Scanner sc = new Scanner(f);
            for (int i = 0; i < MAP_SIZE; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < MAP_SIZE; j++) {
                    mapTile[i][j] = tileId[line.charAt(j)];
                    mapTile[i][j].setXY(Tile.PIXEL_SIZE * i, Tile.PIXEL_SIZE * j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
