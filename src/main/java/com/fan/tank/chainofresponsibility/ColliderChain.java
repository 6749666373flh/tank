package com.fan.tank.chainofresponsibility;

import com.fan.tank.gameObjects.AbstractGameObject;
import com.fan.tank.util.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

public class ColliderChain implements Collider {

    private List<Collider> colliders;

    public ColliderChain() {
        initColliders();
    }

    private void initColliders() {
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        try {
            for (String clsName : colliderNames) {
                Class<?> clazz = Class.forName("com.fan.tank.chainofresponsibility." + clsName);
                Collider c = (Collider) clazz.getConstructor().newInstance();
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        for (Collider collider : colliders) {
            if(!collider.collide(go1,go2)) return false;
        }
        return true;
    }


}
