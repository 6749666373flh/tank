package com.fan.tank.chainofresponsibility;

import com.fan.tank.AbstractGameObject;
import com.fan.tank.Bullet;
import com.fan.tank.Player;
import com.fan.tank.Wall;

public class PlayerWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Player && go2 instanceof Wall) {
            Player p = (Player) go1;
            Wall w = (Wall) go2;

            if(p.isLive() && p.getRect().intersects(w.getRect())) {
                p.back();
            }
        } else if (go1 instanceof Wall && go2 instanceof Player) {
            collide(go2,go1);
        }

        return true;
    }
}
