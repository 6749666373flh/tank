package com.fan.tank.net;

import com.fan.tank.net.msg.Msg;
import com.fan.tank.net.msg.MsgType;
import com.fan.tank.net.msg.TankStopMsg;
import com.fan.tank.util.Direction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TankStopMsgTest {

    @Test
    void encode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        Msg msg = new TankStopMsg(UUID.randomUUID(), 100, 200);

        emChannel.pipeline().addLast(new MsgEncoder());
        emChannel.writeOutbound(msg);
        ByteBuf buf = emChannel.readOutbound();

        int msgType = buf.readInt();
        int len = buf.readInt();

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        boolean b = buf.readBoolean();
        int i = buf.readInt();


        System.out.println("msgType: "+ MsgType.values()[msgType]);
        System.out.println("len: "+len);
        System.out.println("uuid: " + uuid);
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        System.out.println("moving: "+b);
        System.out.println("dir: "+Direction.values()[i]);
    }

    @Test
    void decode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        UUID uuid = UUID.randomUUID();
        Msg msg = new TankStopMsg(UUID.randomUUID(), 100, 200);

        emChannel.pipeline().addLast(new MsgDecoder());
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        buf.writeInt(24);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        buf.writeInt(100);
        buf.writeInt(200);
        buf.writeBoolean(false);
        buf.writeInt(0);

        emChannel.writeInbound(buf);

        Object o = emChannel.readInbound();
        System.out.println(o);
    }

}