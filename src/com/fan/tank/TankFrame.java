package com.fan.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFrame extends Frame {

   Tank myTank;


    public TankFrame(){
        this.setTitle("com.fan.tank.Tank war");
        this.setLocation(400,100);
        this.setSize(800,600);
        myTank = new Tank(100,100,Direction.R);

        this.addKeyListener(new TankKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        myTank.paint(g);
    }

    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyRealeased(e);
        }
    }
}
