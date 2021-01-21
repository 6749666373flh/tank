package com.fan.tank.net.msg;

import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Bullet;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg {

    private UUID playerId;
    private UUID id;
    private int x,y;
    private Direction dir;
    private Group group;

    public BulletNewMsg() {
    }


    public BulletNewMsg(Bullet bullet) {
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir =bullet.getDir();
        this.group =bullet.getGroup();

    }

    public UUID getPlayerId() {
        return playerId;
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

    public Direction getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] bytes = null;
        try {
            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());

            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

            dos.writeInt(x);
            dos.writeInt(y);

            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
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
            this.playerId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Direction.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
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
        if(this.playerId.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;

        Bullet bullet = new Bullet(this.x, this.y, this.dir, this.group, this.playerId);
        bullet.setId(this.id);
        TankFrame.INSTANCE.getGm().add(bullet);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "playerId=" + playerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }
}
