package com.fan.tank.net.msg;

import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.gameObjects.Tank;
import com.fan.tank.net.Client;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg{

    private UUID id;
    private UUID bulletId;

    public TankDieMsg() {
    }

    public TankDieMsg(UUID id, UUID bulletId) {
        this.id = id;
        this.bulletId = bulletId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBulletId() {
        return bulletId;
    }

    public void setBulletId(UUID bulletId) {
        this.bulletId = bulletId;
    }

    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] bytes = null;
        try {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
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

    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.bulletId = new UUID(dis.readLong(), dis.readLong());
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

    public void handle() {
        Bullet bullet = TankFrame.INSTANCE.getGm().findBulletByUUID(this.bulletId);
        if(bullet != null) bullet.die();

        Tank tank = TankFrame.INSTANCE.getGm().findTankByUUID(this.id);

        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
            TankFrame.INSTANCE.getGm().getMyTank().die();
        }else{
            if(tank != null) tank.die();
        }


        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }
}
