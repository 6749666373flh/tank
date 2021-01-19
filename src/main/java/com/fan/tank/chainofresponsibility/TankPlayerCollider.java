package com.fan.tank.chainofresponsibility;

import com.fan.tank.gameObjects.AbstractGameObject;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.gameObjects.Tank;

public class TankPlayerCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Player && go2 instanceof Tank) {
            Player p = (Player) go1;
            Tank t = (Tank) go2;
            if (t.isLive() && t.getRect().intersects(p.getRect())) {
                t.back();
                p.back();
            }
        } else if (go1 instanceof Tank && go2 instanceof Player) {
            collide(go2,go1);
        }

        return true;
    }
}
