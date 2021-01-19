package com.fan.tank.gameObjects;

import com.fan.tank.util.ResourceMgr;

import java.awt.*;

public class Explode extends AbstractGameObject {

    private int x, y;
    private int width, height;
    private int step = 0;
    private boolean live = true;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;

        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean over) {
        this.live = over;
    }

    public void paint(Graphics g) {

        if(!live) return;

        g.drawImage(ResourceMgr.explodes[step++], x, y, null);

        if (step >= ResourceMgr.explodes.length) {
            this.die();
        }


    }

    private void die() {
        this.live = false;
    }


}