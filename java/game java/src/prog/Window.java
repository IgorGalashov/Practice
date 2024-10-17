package prog;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Window {
    public static void main(String[] args) {
       JFrame win = new JFrame("game");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLocation(0,0);
        Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
        win.setSize(screenSize);
        win.add(new Panel());
        win.setVisible(true);
    }
}
