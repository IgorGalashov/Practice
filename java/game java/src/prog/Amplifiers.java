package prog;
import javax.swing.*;
import java.awt.*;

public class Amplifiers {
    private double x;
    private double y;
    private double w;
    private double h;
    private double speed;
    private Image img;
    private int type;

    public Amplifiers(int type, double x, double y) {
        this.type = type;
        switch (type) {
            case (1): {
                this.x = x;
                this.y = y;
                w = 100;
                h = 50;
                speed = 8;
                img = new ImageIcon("Image/apt.png").getImage();
                break;
            }
            case (2): {
                this.x = x;
                this.y = y;
                w = 150;
                h = 100;
                speed = 7;
                double angle = Math.toRadians(Math.random() * 360);
                img = new ImageIcon("Image/en2.png").getImage();
                break;
            }
            case (3): {
                this.x = x;
                this.y = y;
                w = 150;
                h = 150;
                speed = 6;
                double angle = Math.toRadians(Math.random() * 360);
                img = new ImageIcon("Image/en2.png").getImage();
                break;
            }
        }
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public void update(){
        y++;
    }
    public void  draw(Graphics2D g){
        g.drawImage(img,(int)x,(int)y,(int)w,(int) h,null);}
}
