package com.fan.tank.net;

import com.fan.tank.net.msg.MsgType;
import com.fan.tank.net.msg.TankMovingOrDirChangeMsg;
import com.fan.tank.util.Direction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TankMovingOrDirChangeMsgTest {
    @Test
    void encode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        TankMovingOrDirChangeMsg tankMovingOrDirChangeMsg = new TankMovingOrDirChangeMsg(UUID.randomUUID(), 100, 200, Direction.U);

        emChannel.pipeline().addLast(new MsgEncoder());
        emChannel.writeOutbound(tankMovingOrDirChangeMsg);
        ByteBuf buf = emChannel.readOutbound();

        int msgType = buf.readInt();
        int len = buf.readInt();

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        int i = buf.readInt();


        System.out.println("msgType: "+ MsgType.values()[msgType]);
        System.out.println("len: "+len);
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        System.out.println("dir: "+Direction.values()[i]);
        System.out.println("uuid: " + uuid);
    }

    @Test
    void decode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        UUID uuid = UUID.randomUUID();


        emChannel.pipeline().addLast(new MsgDecoder());
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankMovingOrDirChange.ordinal());
        buf.writeInt(28);

        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        buf.writeInt(100);
        buf.writeInt(200);
        buf.writeInt(2);


        emChannel.writeInbound(buf);
        Object o = emChannel.readInbound();
        System.out.println(o.toString());
    }
}