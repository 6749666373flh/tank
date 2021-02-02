package com.fan.tank.chainofresponsibility;

import com.fan.tank.gameObjects.AbstractGameObject;
import com.fan.tank.gameObjects.Ammo;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.gameObjects.Wall;
import com.fan.tank.net.Client;
import com.fan.tank.net.msg.AmmoDieMsg;
import com.fan.tank.net.msg.BulletDieMsg;
import com.fan.tank.util.PropertyMgr;
import com.fan.tank.util.ResourceMgr;
import io.netty.util.internal.StringUtil;

public class PlayerAmmoCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Player && go2 instanceof Ammo) {
            Player p = (Player) go1;
            Ammo a = (Ammo) go2;

            if(p.isLive() && p.getRect().intersects(a.getRect())) {
                p.setBulletNum(p.getBulletNum() + Integer.parseInt(PropertyMgr.get("bulletNum"))); // 补充弹药
                a.die();
                Client.INSTANCE.send(new AmmoDieMsg(a.getId()));
            }
        } else if (go1 instanceof Ammo && go2 instanceof Player) {
            collide(go2,go1);
        }

        return true;
    }
}
