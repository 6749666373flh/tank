package com.fan.tank.net;

import com.fan.tank.gameObjects.Player;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import netty.TankMsg;
import netty.TankMsgEncoder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MsgEncoderTest {

    @Test
    void encode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        Player player = new Player(100, 200, Direction.D, Group.GOOD);
        TankJoinMsg tankJoinMsg = new TankJoinMsg(player);

        emChannel.pipeline().addLast(new MsgEncoder());
        emChannel.writeOutbound(tankJoinMsg);
        ByteBuf buf = emChannel.readOutbound();
        int len = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        int i = buf.readInt();
        boolean b = buf.readBoolean();
        int i1 = buf.readInt();
        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        assertEquals(33,len);
        System.out.println("len: "+len);
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        System.out.println("dir: "+Direction.values()[i]);
        System.out.println("moving: "+b);
        System.out.println("group: "+Group.values()[i1]);
        System.out.println("uuid: " + uuid);
    }
}