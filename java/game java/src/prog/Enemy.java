package prog;
import javax.swing.*;
import java.awt.*;
public class Enemy {
    private double x;
    private double y;// обозначение переменных
    private double w;
    private double h;
    private int r;
    private double speed;
    private double dx;
    private double dy;
    private Color color;
    private int type;
    private long timer_s=0;
    private long timer_f=0;
    private long timer_d=2000;
    private double health;
    private int x1;
    private int damage=2;
    private double dp=0;
    private double wh=100;
    Image img;
    Image hp = new ImageIcon("Image/hp.png").getImage();
    private boolean hp1=false;
    public Rectangle getRect(){
        return  new Rectangle((int) x,(int) y,(int) w,(int)h);
    }
    public Enemy(int type,double x,double y){
              this.type=type;
              switch (type) {
                  case(1) : {
                      this.x =x; // присвоение переменным значения
                      this.y =y;
                      r = 25;
                      w = 100;
                      h = 50;
                      speed = 8;
                      health = 3;
                      double angle = Math.toRadians(Math.random() * 360);
                    dx = Math.sin(angle) * speed;
                    dy = Math.cos(angle) * speed;
                      img = new ImageIcon("Image/enemy2.png").getImage();
                      x1=600;
                      break;
                  }
                  case (2):{
                      this.x =x;
                      this.y =y;
                      r = 25;
                      w = 150;
                      h = 100;
                      speed = 7;
                      health = 5;
                      double angle = Math.toRadians(Math.random() * 360);
                      dx = Math.sin(angle) * speed;
                      dy = Math.cos(angle) * speed;
                      img = new ImageIcon("Image/en2.png").getImage();
                      x1=200;
                      break;
                  }
                  case (3):{
                      this.x =x;
                      this.y =y;
                      r = 25;
                      w = 150;
                      h = 150;
                      speed = 6;
                      health = 10;
                      double angle = Math.toRadians(Math.random() * 360);
                      dx = Math.sin(angle) * speed;
                      dy = Math.cos(angle) * speed;
                      img = new ImageIcon("Image/en2.png").getImage();
                      x1=200;
                      break;
                  }
              }

    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double getH() {return h;}
    public boolean remove(){
        if (health<=0){
            return true;
        }
        return false;
    }
    public void hit(){
        dp=(damage/health)*100;
        hp1=true;
        health-=damage;
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
    public void update(){
    x+=dx;
    y+=dy;
    if (x<0&& dx<0 ) dx=-dx;
    if (x>Panel.WIDTH-300&&dx>0)dx=-dx;
    if (y<0&&dy<0) dy =-dy;
    if (y>x1 &&dy>0) dy=-dy;
    if(timer_rech())// промежуток времени между выстрелами
    Panel.enBullets.add(new EnBullet(type));
    Panel.ex=x;
    Panel.ey=y;
    timer_rech();


    }
        public void  draw(Graphics2D g){//отрисовка вражеских кораблей
            g.drawImage(img,(int)x,(int)y,(int)w,(int) h,null);
            switch (type) {
                case (1): {
                    g.drawImage(hp, (int) x - 4, (int) y + 25, (int) (wh - dp), 50, null);
                     break;}
                case (2):{
                    g.drawImage(hp, (int) x +20, (int) y + 75, (int) (wh - dp),  50, null);
                       break;}
                case (3): {
                    g.drawImage(hp, (int) x +20, (int) y +120, (int) (wh - dp),  50, null);
                     break;}
            }
        }
}
