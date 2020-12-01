package bomber.gameFunction;

import bomber.Game;
import bomber.entity.Entity;
import bomber.StillObject.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map {
    //0-9 TileLimit
    public static final int MAP_HEIGHT = 15;
    public static final int MAP_WIDTH = 28;
    public static final int TILE_TYPE_LIMIT = 10;
    private List<Entity> entityList;

    public List<Entity> getEntityList() {
        return entityList;
    }

    public Tile[][] mapTile = new Tile[MAP_HEIGHT][MAP_WIDTH];
    int[][] mapInfo = new int[MAP_HEIGHT][MAP_WIDTH];
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Tile[] tileId = new Tile[10];
    private Tile floorTile;

    public List<Entity> mapTileArrayToList() {
        List<Entity> n = new ArrayList<Entity>();
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                n.add(mapTile[i][j]);

                //System.out.print(mapTile[i][j].getXY() + " ");
            }
            //System.out.println();
        }
        return n;
    }

    public void changeTile(int x, int y, int id) {

        mapTile[y][x] = new Tile(tileId[id]);
        mapTile[y][x].setXY(x, y);
    }

    public int[][] createNavigationMap() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                mapInfo[i][j] = mapTile[i][j].getCanBePassed() ? 0 : 1;
            }
        }
        for (Entity entity : entityList) {
            mapInfo[(int) entity.getY()][(int) entity.getX()] = entity.getCanBePassed() ? 0 : 1;
        }
        return mapInfo;
    }

    public boolean isTileEmpty(int x, int y) {
        if (validTile(x, y))
            return mapTile[y][x].getCanBePassed();
        else
            return false;
    }

    public boolean validTile(int x, int y) {
        if (x >= 0
                && y >= 0
                && x < Map.MAP_WIDTH - 1
                && y < Map.MAP_HEIGHT - 1)
            return true;
        else
            return false;
    }


    public boolean isTileEmpty(double x, double y) {
       // System.out.println((int) x + " " + (int) y +
              //   (mapTile[(int) y][(int) x].getCanBePassed() ? "true" : "false"));
        return isTileEmpty((int) x, (int) y);
    }

    public boolean isTileDestructible(int x, int y) {
        if(validTile(x,y)) {
            return mapTile[y][x].isDestructible();
        }
        return false;
    }

    public boolean isTileDestructible(double x, double y) {
        return isTileDestructible((int)x,(int)y);
    }

    public boolean destroyTile(double x, double y) {
        return destroyTile((int) x, (int) y);
    }

    public boolean destroyTile(int x, int y) {
        if (validTile(x, y)) {
            Tile tileRef = mapTile[y][x];
            if (tileRef.isDestructible()) {
                if (tileRef.getFloorTile() != null)
                    changeTile(x, y, tileRef.getFloorTile().getId());
                else {
                    //Them roi ra do o day
                }
                return true;
            }
            else
            return  false;
        }
        return false;
    }


    public void loadTile() {

        for (int i = 0; i < TILE_TYPE_LIMIT; i++) {
            //Tile[i] = new Tile()
            String s = String.format(System.getProperty("user.dir") +
                    "\\src\\texture\\map\\%d.png", i);
            tileId[i] = new Tile(s);
            tileId[i].setId(i);
            System.out.println(tileId[i].getId());

        }
        //Plasteel floor
        tileId[0].setCanBePassed(true);
        tileId[0].setDestructible(false);
        //Plasteel wall, indestructible
        tileId[1].setCanBePassed(false);
        tileId[1].setDestructible(false);
        tileId[1].setFloorTile(new Tile(tileId[0], false));
        //Sandstone floor
        tileId[2].setCanBePassed(true);
        tileId[2].setDestructible(false);
        //Natural sandstone wall, destroy after 1 explosion, become sandstone floor
        tileId[3].setCanBePassed(false);
        tileId[3].setDestructible(true);
        tileId[3].setFloorTile(new Tile(tileId[2], false));

        //Cracked sandstone wall, destroyed after 1 explosion, become sandstone floor
        tileId[4].setCanBePassed(false);
        tileId[4].setDestructible(true);
        tileId[4].setFloorTile(new Tile(tileId[2], false));

        //Sandstone brick wall, destroyed after 1 explosion, become cracked sandstone wall
        tileId[5].setCanBePassed(false);
        tileId[5].setDestructible(true);
        tileId[5].setFloorTile(new Tile(tileId[4], false));

        //Gate floor
        tileId[6].setCanBePassed(true);
        tileId[6].setDestructible(false);
        //Gate
        tileId[7].setCanBePassed(false);
        tileId[7].setDestructible(false);
        //Gas vent
        tileId[8].setCanBePassed(false);
        tileId[8].setDestructible(false);
        //plasteel embrasure
        tileId[9].setCanBePassed(false);
        tileId[9].setDestructible(false);
    }

    public Tile getTile(int i, int j) {
        return mapTile[i][j];
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
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
                    //mapTile[i][j] = new Tile(tileId[bufferString.charAt(j) - 48]);

                    changeTile(j, i, bufferString.charAt(j) - 48);

                    //System.out.print(mapTile[j][i].getId());
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
