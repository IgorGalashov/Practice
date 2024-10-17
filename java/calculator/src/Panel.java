import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Panel implements ActionListener {
    JTextArea field;
    String s;
    double zn1=0,zn2=0;
    int id=0;
    Button[] buttons=new Button[18];
    public static void main(String[] args) {
        Panel gui=new Panel();
        gui.go();
    }
    public void go(){
       GridLayout layout=new GridLayout(3,3,10,10);
        JFrame frame=new JFrame();
        JPanel panel=new JPanel();
        JPanel panel1=new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        frame.getContentPane().add(BorderLayout.SOUTH,panel);
        frame.getContentPane().add(BorderLayout.EAST,panel1);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         field= new JTextArea(1,20);
         field.setFont(new Font("s",Font.BOLD,16));
        panel1.add(BorderLayout.CENTER,field);
        panel.setLayout(layout);
        for (int i = 0;i<17;i++){
            buttons[i]= new Button(""+i);
            buttons[i].addActionListener(this);
            id=i;
            panel.add(buttons[i]);
        }
        buttons[0].setLabel("1");
        buttons[1].setLabel("2");
        buttons[2].setLabel("3");
        buttons[6].setLabel("4");
        buttons[7].setLabel("5");
        buttons[8].setLabel("6");
        buttons[12].setLabel("7");
        buttons[13].setLabel("8");
        buttons[14].setLabel("9");
        buttons[3].setLabel("0");
        buttons[9].setLabel("C");
        buttons[15].setLabel("=");
        buttons[4].setLabel("+");
        buttons[5].setLabel("-");
        buttons[10].setLabel("*");
        buttons[11].setLabel("/");
        buttons[16].setLabel(".");
        //buttons[17].setLabel("zv");
        frame.setVisible(true);

    }
  public void actionPerformed(ActionEvent e) {
      Music music=new Music();
      boolean zvuk=true;
        if(e.getSource()==buttons[0])
        {
            field.append("1");
            music.go();
        }
        if(e.getSource()==buttons[1])
        {field.append("2");
            music.go();
        }
        if(e.getSource()==buttons[2])
        {field.append("3");
            music.go();
        }
        if(e.getSource()==buttons[6])
        {field.append("4");
            music.go();
        }
        if(e.getSource()==buttons[7])
        {field.append("5");
            music.go();
        }
        if(e.getSource()==buttons[8])
        {field.append("6");
            music.go();
        }
        if(e.getSource()==buttons[12])
        {field.append("7");
            if (zvuk)
                music.go();
        }
        if(e.getSource()==buttons[13])
        {field.append("8");
            music.go();
        }
        if(e.getSource()==buttons[14])
        {field.append("9");
            music.go();
        }
        if(e.getSource()==buttons[3])
        { field.append("0");
            music.go();
        }
        if(e.getSource()==buttons[9])//C
        { field.setText("");
            music.go();
        }
        if(e.getSource()==buttons[15])//=
        {   music.go();
            s=field.getText();
            zn2=Double.parseDouble(s);
            // System.out.println(zn2);
            double zn = 0;
            switch (id){
                case (1):
                {zn=zn1+zn2;
                  break;
                }
                case (2):{
                  zn=zn1-zn2;
                  break;
                }
                case (3):
                {zn=zn1*zn2;
                    break;
                }
                case (4):{
                    zn=zn1/zn2;
                    break;
                }
            }
            field.setText("");
            field.append(String.valueOf(zn));
        }
        if(e.getSource()==buttons[4])//+
        {
            music.go();
            s=field.getText();
            zn1=Double.parseDouble(s);
            field.setText("");
            id=1;
        }
        if(e.getSource()==buttons[5])//-
        {   music.go();
            s=field.getText();
            zn1=Double.parseDouble(s);
            field.setText("");
            id=2;
            System.out.println(zn1);
        }
        if(e.getSource()==buttons[10])//*
        {
            music.go();
            s=field.getText();
            zn1=Double.parseDouble(s);
            field.setText("");
            id=3;
        }
        if(e.getSource()==buttons[11])
        {
            music.go();
            s=field.getText();
            zn1=Double.parseDouble(s);
            field.setText("");
            id=4;
        }
        if(e.getSource()==buttons[16])
        {
            field.append(".");
            music.go();
        }
       // if(e.getSource()==buttons[17])
        //{if (zvuk)
            //zvuk=false;
            //else zvuk=true;
       // }
  }

}
 class Music{
    public void go() {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            for (int i = 1; i < 2; i += 1) {
                track.add(makeEvent(144, 1, i, 100, i));
            }
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
        }
        return event;
    }
}