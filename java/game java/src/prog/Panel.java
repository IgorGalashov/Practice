package prog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener{
    public static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int mouseX;
    public static int mouseY;
    public static double ex;
    public static double ey;

    public static enum STATES{MENU,PLAY};
    public static STATES states= STATES.PLAY;
    private BufferedImage image;
    private Graphics2D g;
    public static boolean click=false;
   Timer mineTimer= new Timer(30, this);
    Back back= new Back();
    static Player player=new Player();
    //public static ArrayList<Amplifiers>amplifiers;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<EnBullet> enBullets;
    public static Level level=new Level();
    PlayerBut playerBut=new PlayerBut();
    Menu menu=new Menu();
    public Panel()
    {
        super();
       setFocusable(true);
       requestFocus();
        mineTimer.start();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        addMouseListener(new Listeners());
        addKeyListener(new Listeners());
        addMouseMotionListener(new Listeners());
        bullets=new ArrayList<Bullet>();
        enemies=new ArrayList<Enemy>();
        enBullets=new ArrayList<EnBullet>();
       // amplifiers=new ArrayList<Amplifiers>();
    }
    public void actionPerformed(ActionEvent e)
    {
      if (states.equals(STATES.MENU)) {
          back.draw(g);
          menu.draw(g);
          menu.moveBut(menu.button1);
          menu.moveBut(menu.button2);
          menu.moveBut(menu.button3);
          menu.moveBut(menu.button4);
          menu.moveBut(menu.button5);
          gameDraw();
      }

      if (states.equals(STATES.PLAY)){
          gameRender();
          gameDraw();
          gameUpdate();
          player.draw(g);
          }

        }

    public void gameRender()
    {
     back.draw(g);
     player.draw(g);
        for (int i=0;i<bullets.size();i++) {
            bullets.get(i).draw(g);
        }
        for (int i=0;i<enBullets.size();i++){
            enBullets.get(i).draw(g);
        }
        for (int i=0;i<enemies.size();i++){
            enemies.get(i).draw(g);
        }
        if (level.showLevel()){
       // level.draw(g);
            }
    }
    public void gameUpdate(){
        back.update();
        player.update();
       for (int i=0;i<enBullets.size();i++){
           enBullets.get(i).update();
        }
        for (int i=0;i<bullets.size();i++){
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if (remove){
                bullets.remove(i);
                i--;
            }
        }
        for (int i=0;i<enemies.size();i++){
            enemies.get(i).update();
        }

        for (int i=0;i<enemies.size();i++){
            Enemy e=enemies.get(i);
            ex=e.getX();
            ey=e.getY();
            double ew=e.getW();
            double eh=e.getH();
            for (int j=0;j<bullets.size();j++){
                Bullet b=bullets.get(j);
                double bx=b.getX();
                double by=b.getY();
                double bw=b.getW();
                double bh=b.getH();

                if ((bx>ex-bw)&&(bx<ex+ew)&&(by>ey-bh)&&(by<ey+eh)){
                    e.hit();
                    bullets.remove(j);
                    boolean remove=e.remove();
                    if (remove){
                        enemies.remove(i);
                        i--;
                        break;
                    }

                }
            }

        }
        if (player.healse<=0){
            JOptionPane.showMessageDialog(null,"game over");
        }

        level.update();
    }

    private void gameDraw()
    {
        Graphics g2=this.getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();


    }
}
