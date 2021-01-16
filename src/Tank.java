import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    public static final int SPEED = 5;

    private int x, y;
    private Direction dir;
    private boolean bL, bU, bR, bD;

    public Tank(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.dir = direction;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, 30, 30);
        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {

        if (!bL && !bU && !bR && !bD)
            dir = Direction.STOP;
        if (bL && !bU && !bR && !bD)
            dir = Direction.L;
        if (!bL && bU && !bR && !bD)
            dir = Direction.U;
        if (!bL && !bU && bR && !bD)
            dir = Direction.R;
        if (!bL && !bU && !bR && bD)
            dir = Direction.D;

    }

    private void move() {
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
    }

    public void keyRealeased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        setMainDir();
    }

}
