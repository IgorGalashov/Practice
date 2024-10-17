package prog;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Timer;

public class Level {
    private int levelNumber;// обозначение переменных
    private int levelMultipleer;
    private long levelTimer;
    private long levelDelay;
    private long levelTimerDiff;
    private String levelText;
    private long timer_s=0;
    private long timer_f=0;
    private long timer_d=1000;
public Level(){// добавление вражеских кораблей и их типов
    levelMultipleer=3;
    levelNumber=1;
    levelTimer=0;
    levelDelay=2000;
    levelTimerDiff=0;
}
    public boolean timer_rech(){
        if(timer_s==0){
            timer_s=System.currentTimeMillis();
            timer_f=timer_s+timer_d;

        }
        if (timer_f<=System.currentTimeMillis()){
            timer_s=0;
            return true;
        }
        else return false;
    }
   public  void createEnemies(){
    int enemyCout=levelNumber*levelMultipleer;
        switch (levelNumber){
            case (1):{
                Panel.enemies.add(new Enemy(1,500,0));
                Panel.enemies.add(new Enemy(1,600,0));
                Panel.enemies.add(new Enemy(1,700,0));
                //Panel.amplifiers.add(new Amplifiers(1,500,0));
                    break;
                }
            case (2):{
                Panel.enemies.add(new Enemy(2,0,200));
                Panel.enemies.add(new Enemy(2,500,0));
                Panel.enemies.add(new Enemy(2,Panel.HEIGHT,0));
              break;

            }
            case (3):{
                while (enemyCout > 0) {
                    Panel.enemies.add(new Enemy(1,500,0));
                    Panel.enemies.add(new Enemy(2,500,0));
                    enemyCout--;
                }

                break;

            }
            case (4):{
                    Panel.enemies.add(new Enemy(3,500,0));

                break;

            }
            }

    levelNumber++;
    }
    public void update(){
    if (Panel.enemies.size()==0&&levelTimer==0){
        levelTimer=System.nanoTime();}
        if (levelTimer>0){
            levelTimerDiff+=(System.nanoTime()-levelTimer)/1000000;
            levelTimer=System.nanoTime();}
            if (levelTimerDiff>levelDelay) {
                createEnemies();
                levelTimer=0;
                levelTimerDiff=0;
            }
        }
        public boolean showLevel(){
        if (levelTimer!=0) {
            return true;
        }
        else return false;
        }

}
