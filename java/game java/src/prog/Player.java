package prog;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
public class Player {
    private double x;
    private double y;
    private double w;
    private double h;
    private int speed;
   public int healse;
    private double angl;
    private  double distX;
   private  double distY;
    private  double dist;
    public static boolean up;
    public static boolean dawn;
    public static boolean left;
    public static boolean ring;
    public static boolean isFiring;
    Image img = new ImageIcon("Image/ship.png").getImage();
    public Rectangle getRect(){
        return  new Rectangle((int) x,(int) y,100,100);
    }
    public Player ()

    {
        x=650;
        y=650;
        w=100;
        h=100;
        speed=10;
        up=false;
        dawn=false;
        left=false;
        ring=false;
        isFiring=false;
        healse=10;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double getH() {return h;}
    public void hit(){
        healse--;
    }
    public void update(){
        distX=Panel.mouseX-x;
        distY=y-Panel.mouseY;
        dist=(Math.sqrt(distX*distX+distY*distY));
        if (distX>0) angl=Math.acos(distY/dist);
        if (distX<0) angl=-Math.acos(distY/dist);
        if(up && y>20){
            y-=speed;
        }
        if(dawn && y<Panel.HEIGHT - h){
            y+=speed;
        }
        if(left && x>0){
            x-=speed;
        }
        if(ring && x<Panel.WIDTH - w){
            x+=speed;
        }

        if (isFiring){
             Panel.bullets.add(new Bullet());
             isFiring=false;
        }


    }
    public void  draw(Graphics2D g){


        AffineTransform origXform;
        origXform=g.getTransform();
        AffineTransform newXform=(AffineTransform)(origXform.clone());
        newXform.rotate(angl,x+47,y+47);
        g.setTransform(newXform);
        g.drawImage(img,(int)x,(int)y,(int)w,(int) h,null);
        g.setTransform(origXform);
    }
}
