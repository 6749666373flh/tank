package com.fan.tank.observer;

import com.fan.tank.GameModel;
import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Ammo;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.net.Client;
import com.fan.tank.net.msg.AmmoNewMsg;
import com.fan.tank.net.msg.BulletNewMsg;

import java.util.Random;

public class TankFireHandler implements TankFireObserver{
    @Override
    public void actionOn(TankFireEvent e) {
        Random random = new Random();

        if (e.getSource() != null && e.getSource() instanceof Player && e.getSource().getBulletNum() > 0 ) {
            e.getSource().fire();
        }else{
            Ammo ammo = new Ammo(random.nextInt(500) + 100, random.nextInt(500) + 100);
            boolean offer = TankFrame.INSTANCE.getGm().getMyTank().getAmmoBlockingQueue().offer(ammo);
            if (offer) {
                TankFrame.INSTANCE.getGm().add(ammo);
                Client.INSTANCE.send(new AmmoNewMsg(ammo)); // 发送消息
            }

        }
    }
}
