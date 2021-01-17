package com.fan.tank.chainofresponsibility;

import com.fan.tank.AbstractGameObject;

public interface Collider {
    // true:继续 false:终止
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
