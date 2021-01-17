package com.fan.tank;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 800;

    private Player myTank;

    private List<AbstractGameObject> gameObjects;

    private TankFrame() {
        this.setTitle("Tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());

        initGameObjects();

    }

    private void initGameObjects() {
        myTank = new Player(300, 100, Direction.R, Group.GOOD);

        gameObjects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            gameObjects.add(new Tank(100+50*i,200,Direction.D,Group.BAD));
        }
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("bullet:"+bullets.size(),10,50);
//        g.drawString("enemys:"+enemys.size(),10,60);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }
//        for (int i = 0; i < enemys.size(); i++) {
//            if (!enemys.get(i).isLive()) {
//                enemys.remove(i);
//            }else{
//                enemys.get(i).paint(g);
//            }
//        }
//
//        for (int i = 0; i < bullets.size(); i++) {
//            for(int j = 0; j < enemys.size(); j++){
//                bullets.get(i).collidesWithTank(enemys.get(j));
//            }
//            if (!bullets.get(i).isLive()) {
//                bullets.remove(i);
//            } else {
//                bullets.get(i).paint(g);
//            }
//        }
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

    //    public void add(Bullet bullet) {
//        this.bullets.add(bullet);
//    }
    public void add(AbstractGameObject go) {
        gameObjects.add(go);
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
