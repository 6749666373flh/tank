package com.fan.tank.strategy;

import com.fan.tank.gameObjects.Player;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    public void fire(Player p);
}
