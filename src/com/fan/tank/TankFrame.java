package com.fan.tank;


import com.fan.tank.chainofresponsibility.Collider;
import com.fan.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 800;

    private Player myTank;
    private Wall wall;

    private List<Collider> colliders;
    private List<AbstractGameObject> gameObjects;
    private ColliderChain colliderChain;


    private TankFrame() {
        this.setTitle("Tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());

        initGameObjects();

    }

    private void initGameObjects() {
        myTank = new Player(300, 100, Direction.R, Group.GOOD);
        wall = new Wall(300, 400, 50, 200);
        colliderChain = new ColliderChain();

        gameObjects = new ArrayList<>();
        int count = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < count; i++) {
            gameObjects.add(new Tank(100+5*i,200,Direction.D,Group.BAD));
        }
        gameObjects.add(wall);
        gameObjects.add(myTank);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.setColor(c);

        for (int i = 0; i < gameObjects.size(); i++) {
            AbstractGameObject go1 = gameObjects.get(i);
            for (int j = 0; j < gameObjects.size(); j++) {
                AbstractGameObject go2 = gameObjects.get(j);
//                if(!colliderChain.collide(go1,go2)) continue;
                colliderChain.collide(go1, go2);
                if(!go1.isLive()) break;
                if(!go2.isLive()) continue;
            }
            if(!go1.isLive()) {
                gameObjects.remove(i);
                continue;
            }
            go1.paint(g);
        }

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
