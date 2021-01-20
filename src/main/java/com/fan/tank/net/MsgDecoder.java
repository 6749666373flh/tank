package com.fan.tank.net;

import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;
import java.util.UUID;

public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {

        if(buf.readableBytes() < 37) return;

//        int len = buf.readInt();
//        int x = buf.readInt();
//        int y = buf.readInt();
//        int dir = buf.readInt();
//        boolean moving = buf.readBoolean();
//        int group = buf.readInt();
//        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        int len = buf.readInt();
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);

        TankJoinMsg tankJoinMsg = new TankJoinMsg();
        tankJoinMsg.parse(bytes);
        list.add(tankJoinMsg);
    }
}
