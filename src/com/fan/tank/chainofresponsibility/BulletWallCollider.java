package com.fan.tank.chainofresponsibility;

import com.fan.tank.gameObjects.AbstractGameObject;
import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.gameObjects.Wall;

public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Wall) {
            Bullet b = (Bullet) go1;
            Wall w = (Wall) go2;

            if(b.isLive() && b.getRect().intersects(w.getRect())) {
                b.die();
                return false;
            }
        } else if (go1 instanceof Wall && go2 instanceof Bullet) {
            collide(go2,go1);
        }

        return true;
    }
}
