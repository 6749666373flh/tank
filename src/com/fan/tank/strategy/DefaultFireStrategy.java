package com.fan.tank.strategy;

import com.fan.tank.Bullet;
import com.fan.tank.Player;
import com.fan.tank.ResourceMgr;
import com.fan.tank.TankFrame;

public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int newX = p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int newY = p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(newX, newY, p.getDir(), p.getGroup()));
    }
}
