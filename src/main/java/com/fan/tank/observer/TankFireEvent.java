package com.fan.tank.observer;

import com.fan.tank.gameObjects.Player;

public class TankFireEvent {

    Player player;

    public Player getSource() {
        return player;
    }

    public TankFireEvent(Player player) {
        this.player = player;
    }
}
