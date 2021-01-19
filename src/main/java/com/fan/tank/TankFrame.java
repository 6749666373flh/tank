package com.fan.tank;


import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 800;

    private GameModel gm = new GameModel();

    public GameModel getGm() {
        return gm;
    }

    private TankFrame() {
        this.setTitle("Tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    Image offSecreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offSecreenImage == null) {
            offSecreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }

        Graphics graphics = offSecreenImage.getGraphics();
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        graphics.setColor(color);
        paint(graphics);
        g.drawImage(offSecreenImage, 0, 0, null);
    }


    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F1) save();
            else if (key == KeyEvent.VK_F2) load();
            else gm.getMyTank().keyPressed(e);
        }


        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyRealeased(e);
        }
    }

    private void save() {
        FileOutputStream fos = null;
        ObjectOutputStream ops = null;
        try {
            File f = new File("e:/tank.dat");
            fos = new FileOutputStream(f);
            ops = new ObjectOutputStream(fos);
            ops.writeObject(gm);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ops != null) ops.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void load() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File f = new File("e:/tank.dat");
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            this.gm = (GameModel) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
