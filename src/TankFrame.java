import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankFrame extends Frame {

   Tank myTank;


    public TankFrame(){
        this.setTitle("Tank war");
        this.setLocation(400,100);
        this.setSize(800,600);
        myTank = new Tank(100,100,Direction.STOP);

        this.addKeyListener(new TankKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        myTank.paint(g);
    }

    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyRealeased(e);
        }
    }
}
