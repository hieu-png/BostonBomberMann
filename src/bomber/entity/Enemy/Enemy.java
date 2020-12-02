package bomber.entity.Enemy;

import bomber.Game;
import bomber.entity.Pawn;
import bomber.entity.Player;
import bomber.gameFunction.Sound;
import bomber.gameFunction.TimeCounter;

public abstract class Enemy extends Pawn {

    protected int updateRate = 8;
    protected int updateCounter = 0;
    protected int damage = 1;
    protected double noiseRate = 8;
    protected double rdmNoiseRate = Game.randomDouble(noiseRate * 0.75, noiseRate * 1.5f);
    public int strengthPoint = 10;
    TimeCounter noiseIdleCounter = new TimeCounter();
    Player player;
    //------------AIbotCui-------------
    protected EnemyAI enemyAI = new EnemyAI(player, this);


//------------End AIbotCui----------


    public void enemyIdleNoise() {

        if(noiseIdleCounter.getTime() > rdmNoiseRate) {
            Sound.playSound(label + "Idle");
            rdmNoiseRate = Game.randomDouble(noiseRate * 0.75, noiseRate * 1.5f);
            noiseIdleCounter.resetCounter();
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Enemy() {


    }

    public Enemy(String label) {
        super(System.getProperty("user.dir") + "\\src\\texture\\enemyPawn\\" + label + "North.png",
                System.getProperty("user.dir") + "\\src\\texture\\enemyPawn\\" + label + "East.png",
                System.getProperty("user.dir") + "\\src\\texture\\enemyPawn\\" + label + "South.png");
        this.label = label;
        destroyOnDeath = true;
    }

    protected void checkPlayer() {
        if(player != null)
        if (this.isCollidedWith(player)) {
            this.dealDamage(1, player);
        }
    }


}
