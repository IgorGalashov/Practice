package prog;
import javax.swing.*;
import java.awt.*;
public class Menu {
    public static boolean click=false;
    ButMenu button1= new ButMenu(10,10,396,100,"Новый Игрок","Image/but1.png");// объявление кнопок
    ButMenu button2= new ButMenu(10,150,396,100,"Играть","Image/but1.png");
    ButMenu button3= new ButMenu(10,300,396,100,"Настройки","Image/but1.png");
    ButMenu button4= new ButMenu(10,450,396,100,"Правила","Image/but1.png");
    ButMenu button5= new ButMenu(10,600,396,100,"Выход","Image/but1.png");
    public void draw(Graphics2D g){
        button1.draw(g);// отрисовка кнопок меню
        button2.draw(g);
        button3.draw(g);
        button4.draw(g);
        button5.draw(g);
    }
    public void moveBut(ButMenu e){

        if (Panel.mouseX>e.getX()&&Panel.mouseX<e.getX()+e.getW()&&
                Panel.mouseY>e.getY()&& Panel.mouseY<e.getY()+e.getH()){
            e.s="Image/but2.png";
            if(e.equals(button2)){
                if(click)
                {
                    Panel.states= Panel.STATES.PLAY;
                    click=false;
                }
            }
            if (e.equals(button5)){
                if (click){
                    System.exit(0);
                    click=false;
                }
            }
        }
        else{ e.s="Image/but1.png";
        }
    }
    class ButMenu {

    private Color color1;
    private double x;
    private double y;
    private double w;
    private double h;
   public String f;
   public String s;
   public ButMenu(int x,int y,int w,int h,String f, String s)
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
