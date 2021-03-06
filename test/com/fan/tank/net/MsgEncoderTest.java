package com.fan.tank.net;

import com.fan.tank.gameObjects.Player;
import com.fan.tank.net.msg.MsgType;
import com.fan.tank.net.msg.TankJoinMsg;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MsgEncoderTest {

    @Test
    void encode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        Player player = new Player(100, 200, Direction.D, Group.GOOD);
        TankJoinMsg tankJoinMsg = new TankJoinMsg(player);

        emChannel.pipeline().addLast(new MsgEncoder());
        emChannel.writeOutbound(tankJoinMsg);
        ByteBuf buf = emChannel.readOutbound();
        int msgType = buf.readInt();
        int len = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        int i = buf.readInt();
        boolean b = buf.readBoolean();
        int i1 = buf.readInt();
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        System.out.println("msgType: "+ MsgType.values()[msgType]);
        System.out.println("len: "+len);
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        System.out.println("dir: "+Direction.values()[i]);
        System.out.println("moving: "+b);
        System.out.println("group: "+Group.values()[i1]);
        System.out.println("uuid: " + uuid);
    }

    @Test
    void decode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        UUID uuid = UUID.randomUUID();

        Player player = new Player(100, 200, Direction.D, Group.GOOD);
        TankJoinMsg tankJoinMsg = new TankJoinMsg(player);

        emChannel.pipeline().addLast(new MsgDecoder());
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(0);
        buf.writeInt(33);
        buf.writeInt(100);
        buf.writeInt(200);
        buf.writeInt(2);
        buf.writeBoolean(false);
        buf.writeInt(1);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());

        emChannel.writeInbound(buf);
        Object o = emChannel.readInbound();
        System.out.println(o.toString());
    }
}