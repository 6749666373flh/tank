package com.fan.tank.strategy;

import com.fan.tank.*;

public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;

        Direction[] dirs = Direction.values();

        for (Direction d : dirs)
            TankFrame.INSTANCE.add(new Bullet(bX, bY, d, p.getGroup()));
    }
}