package com.fan.tank;

import com.fan.tank.chainofresponsibility.ColliderChain;
import com.fan.tank.gameObjects.AbstractGameObject;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.gameObjects.Tank;
import com.fan.tank.gameObjects.Wall;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Serializable {

    private Player myTank;
    private Wall wall;

    private List<AbstractGameObject> gameObjects;
    private ColliderChain colliderChain;

    public GameModel() {
        initGameObjects();
    }

    public Player getMyTank() {
        return myTank;
    }

    private void initGameObjects() {
        myTank = new Player(300, 100, Direction.R, Group.GOOD);
        wall = new Wall(300, 400, 50, 200);
        colliderChain = new ColliderChain();

        gameObjects = new ArrayList<>();
        int count = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < count; i++) {
            gameObjects.add(new Tank(100+50*i,200,Direction.D,Group.BAD));
        }
        gameObjects.add(wall);
        gameObjects.add(myTank);
    }

    public void add(AbstractGameObject go) {
        gameObjects.add(go);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.setColor(c);

        gameObjects.removeIf(gameObject -> !gameObject.isLive());

        for (int i = 0; i < gameObjects.size(); i++) {
            AbstractGameObject go1 = gameObjects.get(i);
            for (int j = 0; j < gameObjects.size(); j++) {
                AbstractGameObject go2 = gameObjects.get(j);
                colliderChain.collide(go1, go2);
            }
            go1.paint(g);
        }
    }
}
