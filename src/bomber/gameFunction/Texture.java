package bomber.gameFunction;

import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Texture {
    public static final int IMAGE_SIZE = 32;

    private Image image;
    private int x;
     int y;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Color color;

    public Image getImage() {
        return image;
    }

    public Texture(String path) {
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
