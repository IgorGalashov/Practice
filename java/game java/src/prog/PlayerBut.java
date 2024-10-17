package prog;

import javax.swing.*;
import java.awt.*;

public class PlayerBut {
    public static boolean click=false;
    ButMenu button1= new ButMenu(500,700,396,100,"Image/but1.png ","pl");
    public void draw(Graphics2D g){
        button1.draw(g);
    }
    public void mBut(ButMenu e) {

        if (Panel.mouseX > e.getX() && Panel.mouseX < e.getX() + e.getW() &&
                Panel.mouseY > e.getY() && Panel.mouseY < e.getY() + e.getH()) {
            e.s = "Image/but2.png";
            if (e.equals(button1)) {
                if (click) {
                    Panel.states= Panel.STATES.MENU;
                    click=false;
                }
            }
        }
        else{ e.s="Image/but1.png";}
    }
    class ButMenu {

        private Color color1;
        private double x;
        private double y;
        private double w;
        private double h;
        public String f;
        public String s;
        public ButMenu(int x,int y,int w,int h,String s,String f)
        {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.f = f;
            this.s = s;
            color1 = Color.WHITE;

        }
        public double getX() {return this.x;}
        public double getY() {return this.y;}
        public double getW() {return this.w;}
        public double getH() {return this.h;}
        public void draw(Graphics2D g){
            g.drawImage(new ImageIcon(s).getImage(),(int) x,(int) y,null );
            g.setColor(color1);
             Font font=new Font("Arial",Font.ITALIC,60);
            g.setFont(font);
            long length =(int) g.getFontMetrics().getStringBounds(f,g).getWidth();
            g.drawString(f,(int)(x+w/2)-(int)(length/2), (int)y+ (int)(h/3)*2);

        }

    }

}
