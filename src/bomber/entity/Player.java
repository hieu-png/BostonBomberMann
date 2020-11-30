package bomber.entity;

import bomber.gameFunction.Sound;
import bomber.gameFunction.TimeCounter;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Player extends Pawn {

    List<Enemy> enemyList = new ArrayList<>();
    ArrayList<String> input;

    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    TimeCounter tc = new TimeCounter();

    public void checkCollision() {
        if(!enemyList.isEmpty()) {
            for (Enemy enemy : enemyList) {
                if (isCollidedWith(enemy)) {
                    Sound.playerDead();
                }
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

    public void createNormalBomb(){

    }


    public void handleInput() {
        if (!isMoving()) {
            if (input.contains("LEFT")||input.contains("A")) {
                toX--;

            } else


            if (input.contains("RIGHT")||input.contains("D")) {
                toX++;


            } else


            if (input.contains("UP")||input.contains("W")) {
                toY--;


            } else


            if (input.contains("DOWN")||input.contains("S")) {
                toY++;


            } else {

            }
        }
        if(input.contains("SPACE")) {

        }

    }

    @Override
    public void update() {
        //checkCollision();
        updateMapInfo();
        handleInput();
        move();

    }
}
