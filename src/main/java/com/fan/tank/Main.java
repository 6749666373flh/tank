package com.fan.tank;

import com.fan.tank.net.Client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);

        new Thread(()->{
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connect();

    }
}
