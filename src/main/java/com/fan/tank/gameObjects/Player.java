package com.fan.tank.gameObjects;

import com.fan.tank.TankFrame;
import com.fan.tank.net.*;
import com.fan.tank.net.msg.TankJoinMsg;
import com.fan.tank.net.msg.TankMovingOrDirChangeMsg;
import com.fan.tank.net.msg.TankStopMsg;
import com.fan.tank.strategy.FireStrategy;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import com.fan.tank.util.PropertyMgr;
import com.fan.tank.util.ResourceMgr;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class Player extends AbstractGameObject {

    public static final int SPEED = 3;

    private int x, y, width, height;
    private int oldX, oldY;
    private Direction dir;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private boolean live = true;
    private Group group;
    private FireStrategy fireStrategy;
    private Rectangle rect;

    private UUID id = UUID.randomUUID();

    private ArrayBlockingQueue<Bullet> blockingQueue = new ArrayBlockingQueue<>(4);

    public Player(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.dir = direction;
        this.group = group;
        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();
        this.oldX = x;
        this.oldY = y;
        rect = new Rectangle(x, y, width, height);
        this.initFireStrategy();
    }

    public Player(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        this.moving = msg.isMoving();
        this.id = msg.getId();
        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();
        this.rect = new Rectangle(x, y, width, height);
        this.initFireStrategy();
    }

    public ArrayBlockingQueue<Bullet> getBlockingQueue() {
        return blockingQueue;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isMoving() {
        return moving;
    }

    public UUID getId() {
        return id;
    }

    public void paint(Graphics g) {
        if (!this.live) return;

        Color color = g.getColor();
        g.setColor(Color.BLUE);
        g.drawString(id.toString(), x, y - 5);
        g.setColor(color);
        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
        }

        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {

        boolean oldMoving = moving;
        Direction oldDir = dir;

        if (!bL && !bU && !bR && !bD) {
            moving = false;
            Client.INSTANCE.send(new TankStopMsg(this.id, this.x, this.y));
        } else {
            moving = true;
            if (bL && !bU && !bR && !bD)
                dir = Direction.L;
            if (!bL && bU && !bR && !bD)
                dir = Direction.U;
            if (!bL && !bU && bR && !bD)
                dir = Direction.R;
            if (!bL && !bU && !bR && bD)
                dir = Direction.D;
//            Client.INSTANCE.send(new TankStartMovingMsg(this.id,this.x,this.y,this.dir));
            if (!oldMoving) Client.INSTANCE.send(new TankMovingOrDirChangeMsg(this.id, this.x, this.y, this.dir));
            if (!oldDir.equals(dir)) Client.INSTANCE.send(new TankMovingOrDirChangeMsg(this.id, this.x, this.y, this.dir));
        }

    }

    private void move() {
        if (!moving) return;
        oldX = x;
        oldY = y;
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
        boundsCheck();
//        Client.INSTANCE.send(new TankStartMovingMsg(this.id,this.x,this.y,this.dir));
        rect.x = x;
        rect.y = y;
    }

    public void keyRealeased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                if(live) fire();
                break;
        }
        setMainDir();
    }

    private void initFireStrategy() {

        String className = PropertyMgr.get("tankFireStrategy");
        try {
            Class clazz = Class.forName("com.fan.tank.strategy." + className);
            fireStrategy = (FireStrategy) (clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire() {
        fireStrategy.fire(this);
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH - width || y > TankFrame.GAME_HEIGHT - height) {
            back();
        }

    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explode(x, y));
    }

}
