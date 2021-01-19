package com.fan.tank.chainofresponsibility;

import com.fan.tank.gameObjects.AbstractGameObject;
import com.fan.tank.gameObjects.Tank;
import com.fan.tank.gameObjects.Wall;

public class TankWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Wall && go2 instanceof Tank) {
            Wall w = (Wall) go1;
            Tank t = (Tank) go2;
            if (t.isLive() && t.getRect().intersects(w.getRect())) {
                t.back();
            }
        } else if (go1 instanceof Tank && go2 instanceof Wall) {
            collide(go2,go1);
        }
        return true;
    }
}
