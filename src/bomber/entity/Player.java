package bomber.entity;

import bomber.gameFunction.Sound;
import bomber.gameFunction.TimeCounter;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Player extends Pawn {

    List<Enemy> enemyList;
    Scene scene;
    ArrayList<String> input = new ArrayList<>();
    TimeCounter tc = new TimeCounter();
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void checkCollision() {
        for (Enemy enemy : enemyList) {
            if (isCollidedWith(enemy)) {
                Sound.playerDead();
            }
        }
    }

    @Override
    public void start() {

    }

    public Player() {
        super(System.getProperty("user.dir") + "\\src\\texture\\" + "player" + "North.png",
                System.getProperty("user.dir") + "\\src\\texture\\" + "player" + "East.png",
                System.getProperty("user.dir") + "\\src\\texture\\" + "player" + "South.png");
        this.label = "player";
        canBePassed = true;
    }

    public void getInput() {
        scene.setOnKeyPressed(keyEvent -> {
            String code = keyEvent.getCode().toString();
            System.out.println("Input detected: " + code);

            if (!input.contains(code))
                input.add(code);
        });
        scene.setOnKeyReleased(keyEvent -> {
            String code = keyEvent.getCode().toString();
            System.out.println("Input released: " + code);
            input.remove(code);
        });
    }

    public void handleInput() {
        if (!isMoving()) {
            if (input.contains("LEFT")||input.contains("A")) {
                toX--;
                Sound.playerDead();
                //input.remove("LEFT");
            } else


            if (input.contains("RIGHT")||input.contains("D")) {
                toX++;
                Sound.enemyDead1();
                //input.remove("RIGHT");

            } else


            if (input.contains("UP")||input.contains("W")) {
                toY--;
                Sound.getItem();
                //input.remove("UP");

            } else


            if (input.contains("DOWN")||input.contains("S")) {
                toY++;
                Sound.no();
                //input.remove("DOWN");

            } else {

            }
        }
        if(input.contains("SPACE")) {
        }

    }

    @Override
    public void update() {
        updateMapInfo();
        getInput();
        handleInput();
        move();
    }
}
