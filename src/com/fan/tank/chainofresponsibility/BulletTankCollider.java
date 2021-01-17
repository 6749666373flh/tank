package com.fan.tank.chainofresponsibility;

import com.fan.tank.AbstractGameObject;
import com.fan.tank.Bullet;
import com.fan.tank.Tank;
import org.w3c.dom.css.Rect;

public class BulletTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank) {
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            if (!t.isLive() || !b.isLive()) return false;
            if (t.getGroup() == b.getGroup()) return true;

            if (b.getRect().intersects(t.getRect())) {
                t.die();
                b.die();
                return false;
            }
        } else if (go1 instanceof Tank && go2 instanceof Bullet) {
            collide(go2,go1);
        }

        return true;
    }
}
