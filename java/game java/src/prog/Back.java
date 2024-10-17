package prog;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Back {
    Image img = new ImageIcon("Image/fon3.jpg").getImage(); // добавление фоновой картинки в игровом режиме
    Image img2 = new ImageIcon("Image/fon3.jpg").getImage();
    private static int xmove1=0; //добавление переменных
    private static int xmove2=-1300;
    private static int speed=20;
    Image adress[]= {img,img2};
    public void draw(Graphics2D g)
    {
        Color backColor = new Color(50,150,50);
        g.setColor(backColor);
        if (Panel.states.equals(Panel.STATES.MENU)) g.fillRect(0,0,Panel.WIDTH,Panel.HEIGHT);
        if (Panel.states.equals(Panel.STATES.PLAY)) {
            g.drawImage(adress[0],(int) 0,(int) xmove1,null);
            g.drawImage(adress[1],(int) 0,(int) xmove2,null);
            }
    }
    public void update(){// содание движения фона
        xmove1+=speed;
        xmove2+=speed;
         if (xmove1==1300){
            xmove1=0;}
        if (xmove2==0){
           xmove2=-1300;}

    }
}
