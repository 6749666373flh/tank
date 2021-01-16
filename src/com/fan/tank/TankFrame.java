package com.fan.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFrame extends Frame {

    private Tank myTank;
    private Tank enemy;

    private Bullet bullet;

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 800;


    public TankFrame() {
        this.setTitle("Tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        myTank = new Tank(300, 100, Direction.R, Group.GOOD, this);
        enemy = new Tank(50, 50, Direction.R, Group.BAD, this);

        bullet = new Bullet(100, 100, Direction.D, Group.GOOD);

        this.addKeyListener(new TankKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        myTank.paint(g);
        enemy.paint(g);

        bullet.paint(g);
    }

    Image offSecreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offSecreenImage == null) {
            offSecreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }

        Graphics graphics = offSecreenImage.getGraphics();
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        graphics.setColor(color);
        paint(graphics);
        g.drawImage(offSecreenImage, 0, 0, null);
    }

    public void add(Bullet bullet) {
        this.bullet = bullet;
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
