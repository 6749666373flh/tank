package com.fan.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    public static final int SPEED = 5;

    private int x, y;
    private Direction dir;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private Group group;

    TankFrame tf;

    public Tank(int x, int y, Direction direction, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = direction;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
//        g.fillRect(x, y, 30, 30);
        if (this.group == Group.GOOD) {
            switch (dir) {
                case L:
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                    break;
            }
        } else if (this.group == Group.BAD) {
            switch (dir) {
                case L:
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                    break;
            }
        }

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
            moving = false;
        else {
            moving = true;
            if (bL && !bU && !bR && !bD)
                dir = Direction.L;
            if (!bL && bU && !bR && !bD)
                dir = Direction.U;
            if (!bL && !bU && bR && !bD)
                dir = Direction.R;
            if (!bL && !bU && !bR && bD)
                dir = Direction.D;
        }


    }

    private void move() {
        if (!moving) return;
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
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();
    }

    private void fire() {
        tf.add(new Bullet(x, y, dir, group));
    }

}
