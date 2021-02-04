package com.fan.tank.gameObjects;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractGameObject implements Serializable {
    public void paint(Graphics g){
        throw new UnsupportedOperationException();
    }

    public boolean isLive(){
        throw new UnsupportedOperationException();
    }
}
