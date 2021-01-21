package com.fan.tank.net.msg;

import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Bullet;

import java.io.*;
import java.util.UUID;

public class BulletDieMsg extends Msg{

    private UUID bulletId1;
    private UUID bulletId2;

    public BulletDieMsg() {
    }

    public BulletDieMsg(UUID bulletId1, UUID bulletId2) {
        this.bulletId1 = bulletId1;
        this.bulletId2 = bulletId2;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] bytes = null;
        try {
            dos.writeLong(bulletId1.getMostSignificantBits());
            dos.writeLong(bulletId1.getLeastSignificantBits());

            dos.writeLong(bulletId2.getMostSignificantBits());
            dos.writeLong(bulletId2.getLeastSignificantBits());

            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.bulletId1 = new UUID(dis.readLong(), dis.readLong());
            this.bulletId2 = new UUID(dis.readLong(), dis.readLong());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        Bullet b1 = TankFrame.INSTANCE.getGm().findBulletByUUID(bulletId1);
        Bullet b2 = TankFrame.INSTANCE.getGm().findBulletByUUID(bulletId2);
        if(b1.getGroup() == b2.getGroup()) return;

        if(b1.isLive()) b1.die();
        if(b2.isLive()) b2.die();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletDie;
    }
}
