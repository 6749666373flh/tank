package com.fan.tank.strategy;

import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.net.msg.BulletNewMsg;
import com.fan.tank.net.Client;
import com.fan.tank.util.ResourceMgr;
import com.fan.tank.TankFrame;

import java.util.concurrent.ArrayBlockingQueue;

public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int newX = p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int newY = p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        Bullet bullet = new Bullet(newX, newY, p.getDir(), p.getGroup(), p.getId());
        ArrayBlockingQueue<Bullet> blockingQueue = TankFrame.INSTANCE.getGm().getMyTank().getBlockingQueue();
        if(blockingQueue.offer(bullet)) {
            TankFrame.INSTANCE.getGm().add(bullet);
        //  System.out.println(new Bullet(newX, newY, p.getDir(), p.getGroup()));
            Client.INSTANCE.send(new BulletNewMsg(bullet)); // 发送消息
        }
    }
}
