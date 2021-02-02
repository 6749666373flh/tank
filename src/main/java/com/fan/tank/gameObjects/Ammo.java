package com.fan.tank.gameObjects;

import com.fan.tank.TankFrame;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import com.fan.tank.util.ResourceMgr;

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class Ammo extends AbstractGameObject {

    private UUID id = UUID.randomUUID();

    private int x, y, w, h;
    private boolean live = true;
    private Rectangle rect;



    public Ammo(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = ResourceMgr.ammo.getWidth();
        this.h = ResourceMgr.ammo.getHeight();
        rect = new Rectangle(x, y, w, h);

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.ammo, x, y, null);
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



}
