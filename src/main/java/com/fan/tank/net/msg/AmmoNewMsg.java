package com.fan.tank.net.msg;

import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Ammo;
import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;

import java.io.*;
import java.util.UUID;

public class AmmoNewMsg extends Msg {


    private UUID id;
    private int x,y;

    public AmmoNewMsg() {
    }

    public AmmoNewMsg(Ammo ammo) {

        this.id = ammo.getId();
        this.x = ammo.getX();
        this.y = ammo.getY();


    }


    public UUID getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] bytes = null;
        try {


            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

            dos.writeInt(x);
            dos.writeInt(y);

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

            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();

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
        if(TankFrame.INSTANCE.getGm().findAmmoByUUID(this.id) != null) return;

        Ammo ammo = new Ammo(this.x, this.y);
        ammo.setId(this.id);
        TankFrame.INSTANCE.getGm().add(ammo);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.AmmoNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
