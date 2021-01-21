package com.fan.tank.net.msg;

import com.fan.tank.TankFrame;
import com.fan.tank.gameObjects.Player;
import com.fan.tank.gameObjects.Tank;
import com.fan.tank.net.Client;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg{

    private int x,y;
    private Direction dir;
    private boolean moving;
    private Group group;

    private UUID id;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Player player) {
        this.x = player.getX();
        this.y = player.getY();
        this.dir = player.getDir();
        this.moving = player.isMoving();
        this.group = player.getGroup();
        this.id = player.getId();
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

    public boolean isMoving() {
        return moving;
    }

    public Group getGroup() {
        return group;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] bytes = null;
        try {
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Direction.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
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
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        if(TankFrame.INSTANCE.getGm().findTankByUUID(this.id) != null) return;

        Tank tank = new Tank(this);
//        Player player = new Player(this);
        TankFrame.INSTANCE.getGm().add(tank);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }
}
