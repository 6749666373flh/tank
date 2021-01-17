package com.fan.tank.chainofresponsibility;

import com.fan.tank.AbstractGameObject;
import com.fan.tank.Bullet;
import com.fan.tank.Player;
import com.fan.tank.Tank;

public class BulletBulletCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Bullet) {
            Bullet b1 = (Bullet) go1;
            Bullet b2 = (Bullet) go2;
            if(b1.getGroup() == b2.getGroup()) return true;

            if (b1.isLive() && b2.isLive() && b1.getRect().intersects(b2.getRect())) {
                b1.die();
                b2.die();
                return false;
            }
        }

        return true;
    }
}
