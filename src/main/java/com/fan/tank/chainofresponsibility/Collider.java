package com.fan.tank.chainofresponsibility;

import com.fan.tank.gameObjects.AbstractGameObject;

import java.io.Serializable;

public interface Collider extends Serializable {
    // true:继续 false:终止
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
