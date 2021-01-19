package com.fan.tank.strategy;

import com.fan.tank.*;
import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.util.Direction;
import com.fan.tank.util.ResourceMgr;

public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;

        Direction[] dirs = Direction.values();

        for (Direction d : dirs) {
            TankFrame.INSTANCE.getGm().add(new Bullet(bX, bY, d, p.getGroup()));
//            System.out.println(new Bullet(bX, bY, d, p.getGroup()));
        }

    }
}