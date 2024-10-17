package prog;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
public class EnBullet {
    private double x;// обозначение переменных
    private double y;
    private double w;
    private double distX;
    private  double distY;
    private double dist;
    private double h;
    private int r;
    private int speed;
    Image img;
    private int type;
    public EnBullet(int type) {
        this.type=type;// присвоение переменным значения
        x = Panel.ex;
        y = Panel.ey;
        w=30;
        h=50;
        r=2;
        speed=10;
        switch (type){
            case (1):{
             img= new ImageIcon("Image/b1.png").getImage();
             break;
            }
            case (2):{
                img= new ImageIcon("Image/b2.png").getImage();
                break;
            }
            case (3):{
                img= new ImageIcon("Image/b2.png").getImage();
                break;
            }

        }
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double getH() {return h;}

    public boolean remove(){
        if (y<0){
            return true;
        }
        return false;
    }
    public void update(){
        y=y+speed;
    }
    public void  draw(Graphics2D g){// отрисовка пуль вражеских кораблей
        switch (type) {
            case (1): {
                g.drawImage(img, (int) x + 40, (int) y, (int) w, (int) h, null);
                break;
            }
            case (2): {
                g.drawImage(img, (int) x + 40, (int) y, (int) w, (int) h, null);
                g.drawImage(img, (int) x + 80, (int) y, (int) w, (int) h, null);
                break;
            }
            case (3):{
                g.drawImage(img, (int) x + 20, (int) y, (int) w, (int) h, null);
                g.drawImage(img, (int) x + 40, (int) y, (int) w, (int) h, null);
                g.drawImage(img, (int) x + 60, (int) y, (int) w, (int) h, null);
                break;
            }

        }
    }
}