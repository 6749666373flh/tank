package com.fan.tank.strategy;

import com.fan.tank.*;
import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.net.msg.BulletNewMsg;
import com.fan.tank.net.Client;
import com.fan.tank.util.Direction;
import com.fan.tank.util.ResourceMgr;

public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;

        Direction[] dirs = Direction.values();

        for (Direction d : dirs) {
            Bullet bullet = new Bullet(bX, bY, d, p.getGroup(), p.getId());
            TankFrame.INSTANCE.getGm().add(bullet);
            Client.INSTANCE.send(new BulletNewMsg(bullet));
//            System.out.println(new Bullet(bX, bY, d, p.getGroup()));
        }

    }
}