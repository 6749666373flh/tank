package com.fan.tank.net.msg;

import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Ammo;
import com.fan.tank.gameObjects.Bullet;

import java.io.*;
import java.util.UUID;

public class AmmoDieMsg extends Msg{

    private UUID ammoId;


    public AmmoDieMsg() {
    }

    public AmmoDieMsg(UUID ammoId) {
        this.ammoId = ammoId;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] bytes = null;
        try {

            dos.writeLong(ammoId.getMostSignificantBits());
            dos.writeLong(ammoId.getLeastSignificantBits());

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
            this.ammoId = new UUID(dis.readLong(), dis.readLong());

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
        Ammo a = TankFrame.INSTANCE.getGm().findAmmoByUUID(ammoId);

        if(a.isLive()) a.die();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.AmmoDie;
    }
}
