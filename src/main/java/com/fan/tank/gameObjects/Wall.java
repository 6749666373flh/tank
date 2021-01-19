package com.fan.tank.gameObjects;

import java.awt.*;

public class Wall extends AbstractGameObject {
    private int x, y, w, h;
    private Rectangle rect;
    private boolean live = true;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        rect = new Rectangle(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);

        Color old = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(old);
    }

    @Override
    public boolean isLive() {
        return live;
    }

    public Rectangle getRect() {
        return rect;
    }
}
