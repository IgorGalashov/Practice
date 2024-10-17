package prog;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
public class Bullet {
    private double x;
    private double y;// добавление переменных
    private double w;
    private double distX;
    private  double distY;
    private double dist;
    private double h;
    private int r;
    private int speed;
    Image img = new ImageIcon("Image/b2.png").getImage();
    public Bullet(){
        x=47+Panel.player.getX();// присвоение переменным знычения
        y=47+Panel.player.getY();
        w=30;
        h=50;
        r=2;
        speed=15;
        distX=Panel.mouseX - x;
        distY=y-Panel.mouseY;
        dist=Math.sqrt(distX*distX+distY*distY);
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

        y=y-speed*distY/dist;// дистанция, по которой дожна лететь пуля
        x=x+speed*distX/dist;
    }
    public void  draw(Graphics2D g){// отрисовка пули и её расположение относительно игрового космического корабля


        AffineTransform origXform;
        origXform=g.getTransform();
        AffineTransform newXform=(AffineTransform)(origXform.clone());
       if(distX>0) newXform.rotate(Math.acos(distY/dist),x,y);
        if(distX<0) newXform.rotate(-Math.acos(distY/dist),x,y);
        g.setTransform(newXform);
        g.drawImage(img,(int)x,(int)y,(int)w,(int) h,null);
        g.setTransform(origXform);
    }
}
