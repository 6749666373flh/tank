package com.fan.tank.net;

import com.fan.tank.net.msg.Msg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

/*public class MsgEncoder extends MessageToByteEncoder<Msg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}*/
public class MsgEncoder extends MessageToMessageEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
//                byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        DatagramPacket packet = new DatagramPacket(byteBuf, new InetSocketAddress("127.0.0.1", 8888));
        out.add(packet);
    }
}