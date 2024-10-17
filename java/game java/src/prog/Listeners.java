package prog;
import java.awt.event.*;
public class Listeners implements MouseListener,KeyListener,MouseMotionListener {
    private boolean isFiring_on;
    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();
        if (key==KeyEvent.VK_W){
            Player.up=true;
        }
        if (key==KeyEvent.VK_S){
            Player.dawn=true;
        }
        if (key==KeyEvent.VK_A){
            Player.left=true;
        }
        if (key==KeyEvent.VK_D){
            Player.ring=true;
        }
        if (key==KeyEvent.VK_SPACE){
            if (isFiring_on)
            Player.isFiring=true;
            isFiring_on=false;
        }
        if (key==KeyEvent.VK_ESCAPE){
            if(Panel.states==Panel.STATES.PLAY)
                Panel.states=Panel.STATES.MENU;
        }
    }
    public void keyReleased(KeyEvent e){
        int key=e.getKeyCode();
        if (key==KeyEvent.VK_W){
            Player.up=false;
        }
        if (key==KeyEvent.VK_S){
            Player.dawn=false;
        }
        if (key==KeyEvent.VK_A){
            Player.left=false;
        }
        if (key==KeyEvent.VK_D){
            Player.ring=false;
        }
        if (key==KeyEvent.VK_SPACE){
            Player.isFiring=false;
            isFiring_on=true;
        }
    }
    public void keyTyped(KeyEvent e){

    }
    public void mouseClicked(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){
        if (e.getButton()==MouseEvent.BUTTON1){
            if (Panel.states==Panel.STATES.MENU){
                Menu.click=true;
            }
        }

    }
    public void mouseReleased(MouseEvent e){
        if (e.getButton()==MouseEvent.BUTTON1){
            if (Panel.states==Panel.STATES.MENU){
                Menu.click=false;
            }
        }
    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseDragged(MouseEvent e){

    }
    public void mouseMoved(MouseEvent e){
        Panel.mouseX=e.getX();
        Panel.mouseY=e.getY();

    }
}