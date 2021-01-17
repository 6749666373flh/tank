package com.fan.tank;

import java.awt.*;

public class Bullet extends AbstractGameObject{

    private int x, y;
    private Direction dir;
    private Group group;
    private boolean live = true;

    public static final int SPEED = 8;

    public Bullet(int x, int y, Direction dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        if (this.group == Group.GOOD) {
            switch (dir) {
                case L:
                    g.drawImage(ResourceMgr.bulletL, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.bulletR, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.bulletU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.bulletD, x, y, null);
                    break;
            }
        }else if (this.group == Group.BAD) {
            switch (dir) {
                case L:
                    g.drawImage(ResourceMgr.bulletL, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.bulletR, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.bulletU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.bulletD, x, y, null);
                    break;
            }
        }

        move();
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
        boundsCheck();
    }

    public void collidesWithTank(Tank tank) {
        if(!tank.isLive() || !this.isLive()) return;
        if(tank.getGroup() == this.group) return;

        Rectangle rect = new Rectangle(x, y, ResourceMgr.bulletU.getWidth(), ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());
        if (rect.intersects(rectTank)) {
            tank.die();
            this.die();
        }
    }

    private void die() {
        this.setLive(false);
    }

    private void boundsCheck() {
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            this.live = false;
        }
    }

}
