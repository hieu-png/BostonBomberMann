package bomber.entity;

public class Enemy extends Pawn {

    Player player;

    public Enemy() {


    }

    public Enemy(String label){
        super(System.getProperty("user.dir") + "\\src\\texture\\enemyPawn\\" + label + "North.png",
                System.getProperty("user.dir") + "\\src\\texture\\enemyPawn\\" + label + "East.png",
                System.getProperty("user.dir") + "\\src\\texture\\enemyPawn\\" + label + "South.png");
        this.label = label;
    }

    @Override
    public void update() {

    }
}
