package bomber.entity;

import bomber.Game;
import bomber.entity.Enemy.Enemy;
import bomber.gameFunction.Sound;
import bomber.gameFunction.TimeCounter;

import java.util.ArrayList;
import java.util.List;

public class Player extends Pawn {
    List<Bomb> bombs;
    List<Enemy> enemyList = new ArrayList<>();
    ArrayList<String> input;

    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    static final int numberOfBombType = 3;
    public int bombRangeBonus = 0;
    private int selectedBomb = 0;
    public TimeCounter[] bombCountdownCounter;
    public double[] bombCoolDown;

    TimeCounter tc = new TimeCounter();

    public void checkCollision() {
        if (!enemyList.isEmpty()) {
            for (Enemy enemy : enemyList) {
                if (isCollidedWith(enemy)) {
                    Sound.playerDead();
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void start() {

    }

    public Player() {
        super(Game.textureFolderPath + "player" + "North.png",
                Game.textureFolderPath + "player" + "East.png",
                Game.textureFolderPath + "player" + "South.png");
        this.label = "player";
        canBePassed = true;
        this.setSpeed(2);
        bombCountdownCounter = new TimeCounter[numberOfBombType];
        for (int i = 0; i < numberOfBombType; i++) {
            bombCountdownCounter[i] = new TimeCounter();
        }
        double[] b = {2.5, 10, 15};
        bombCoolDown = b;
    }

    public void createNormalBomb() {

    }


    public void handleInput() {
        if (!isMoving()) {
            if (input.contains("LEFT") || input.contains("A")) {
                toX--;

            } else if (input.contains("RIGHT") || input.contains("D")) {
                toX++;


            } else if (input.contains("UP") || input.contains("W")) {
                toY--;


            } else if (input.contains("DOWN") || input.contains("S")) {
                toY++;


            } else {

            }
        }
        if (input.contains("SPACE")) {
            placeBomb(selectedBomb, 1 + bombRangeBonus, 2, 1, "explosionBig");
            //System.out.println("Speed : " + this.speed*(double) Texture.IMAGE_SIZE);
            System.out.println(selectedBomb);

        }
        for (int i = 0; i < numberOfBombType; i++) {
            if (input.contains("DIGIT" + (i + 1))) {
                selectedBomb = i;
            }
        }

    }

    public void placeBomb(int bombType, int range, double fuseTime, int bombPenetration, String bombSound) {
        if (bombCountdownCounter[bombType].getTime() > bombCoolDown[bombType]) {
            mapRef.getGame().addEntity(new Bomb(x, y, range, fuseTime,
                    mapRef, bombType + 1, bombPenetration, bombSound));
            bombCountdownCounter[bombType].resetCounter();
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
