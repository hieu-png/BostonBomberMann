package bomber.gameFunction;

import bomber.entity.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Map {
    //0-9 TileLimit
    public final int MAP_HEIGHT = 8;
    public final int MAP_WIDTH = 16;
    public final int TILE_TYPE_LIMIT = 10;
    public Tile[][] mapTile = new Tile[MAP_HEIGHT][MAP_WIDTH];
    public Tile[] tileId = new Tile[10];


    public void loadTile() {

        for (int i = 0; i < TILE_TYPE_LIMIT; i++) {
            //Tile[i] = new Tile()
            String s = String.format(System.getProperty("user.dir") +
                    "\\src\\texture\\map\\%d.png", i);
            tileId[i] = new Tile();

        }
        //0 plasteel outer wall, indestructible
        tileId[0].setCanBePassed(false);
        tileId[0].setDestructible(false);
        //1 Brick wall, destroyed after 2 explosion
        tileId[1].setCanBePassed(false);
        tileId[1].setDestructible(true);
        tileId[1].setHealth(2);
        //2 Stone wall, destroyed after 1 explosion
        tileId[2].setCanBePassed(false);
        tileId[2].setDestructible(true);
        tileId[2].setHealth(1);
        //3 Locked gate
        tileId[3].setCanBePassed(false);
        tileId[3].setDestructible(false);
        //4 unlocked gate way
        tileId[4].setCanBePassed(true);
        tileId[4].setDestructible(false);
        //5 stone floor tile
        tileId[5].setCanBePassed(true);
        tileId[5].setDestructible(false);
        //6 plasteel checked tile
        tileId[5].setCanBePassed(true);
        tileId[5].setDestructible(false);
        //7 plasteel wall will embrasure
        tileId[5].setCanBePassed(false);
        tileId[5].setDestructible(false);
        //8 gas vent
        tileId[5].setCanBePassed(false);
        tileId[5].setDestructible(false);
        //9 collapsed stone
        tileId[5].setCanBePassed(false);
        tileId[5].setDestructible(false);
    }

    public Tile getTile(int i, int j) {
        return mapTile[i][j];
    }

    public void loadMap(String path) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            String bufferString;
            for (int i = 0; i < MAP_HEIGHT; i++) {
                bufferString = br.readLine();
                for (int j = 0; j < MAP_WIDTH; j++) {
                    System.out.print(bufferString.charAt(j));
                    mapTile[i][j] = new Tile(tileId[bufferString.charAt(j)-48]);
                    mapTile[i][j].setXY(Texture.IMAGE_SIZE * i, Texture.IMAGE_SIZE * j);
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
