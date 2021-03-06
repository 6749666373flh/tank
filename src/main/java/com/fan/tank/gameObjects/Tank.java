package com.fan.tank.gameObjects;

import com.fan.tank.TankFrame;
import com.fan.tank.net.msg.TankJoinMsg;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import com.fan.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank extends AbstractGameObject {

    public static final int SPEED = 3;

    private int x, y;
    private int oldX, oldY;
    private Direction dir;
    private boolean bL, bU, bR, bD;
    private boolean moving = true;
    private boolean live = true;
    private Group group;
    private Random r = new Random();
    private int height, width;
    private Rectangle rect;

    private UUID id;

    public Tank(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.dir = direction;
        this.group = group;
        this.height = ResourceMgr.badTankU.getHeight();
        this.width = ResourceMgr.badTankU.getWidth();
        this.rect = new Rectangle(x, y, width, height);
    }

    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        this.moving = msg.isMoving();
        this.id = msg.getId();
        this.height = ResourceMgr.badTankU.getHeight();
        this.width = ResourceMgr.badTankU.getWidth();
        this.rect = new Rectangle(x, y, width, height);
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Rectangle getRect() {
        return rect;
    }

    public UUID getId() {
        return id;
    }

    public void paint(Graphics g) {
//        g.fillRect(x, y, 30, 30);
        if (!this.live) return;

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

//        randomDir();
        if (r.nextInt(100) > 90) {
//            fire();
        }
        rect.x = x;
        rect.y = y;
    }

    private void randomDir() {
        if (r.nextInt(100) > 90) {
            this.dir = Direction.randomDir();
        }
    }

    private void fire() {
        int newX = x + this.width / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int newY = y + this.height / 2 - ResourceMgr.bulletU.getHeight() / 2;
        TankFrame.INSTANCE.getGm().add(new Bullet(newX, newY, this.dir, this.group,this.id));
//        System.out.println(new Bullet(newX, newY, dir, group));
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
