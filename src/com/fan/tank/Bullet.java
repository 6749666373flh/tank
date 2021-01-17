package com.fan.tank;

import java.awt.*;

public class Bullet extends AbstractGameObject {

    private int x, y, w, h;
    private Direction dir;
    private Group group;
    private boolean live = true;
    private Rectangle rect;
    private Rectangle rectTank;
    public static final int SPEED = 6;

    public Bullet(int x, int y, Direction dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.w = ResourceMgr.bulletU.getWidth();
        this.h = ResourceMgr.bulletU.getHeight();
        rect = new Rectangle(x, y, w, h);
        rectTank = new Rectangle(0,0,ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
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
        rect.x = x;
        rect.y = y;

        boundsCheck();
    }


    public Rectangle getRect() {
        return rect;
    }

    public void die() {
        this.setLive(false);
    }

    private void boundsCheck() {
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            this.live = false;
        }
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", dir=" + dir +
                ", group=" + group +
                ", live=" + live +
                ", rect=" + rect +
                ", rectTank=" + rectTank +
                '}';
    }
}
