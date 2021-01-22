package com.fan.tank.net;

import com.fan.tank.net.msg.Msg;
import com.fan.tank.net.msg.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/*public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {

        if(buf.readableBytes() < 8) return;
        buf.markReaderIndex(); // 标记读指针

        MsgType msgType = MsgType.values()[buf.readInt()];

        int len = buf.readInt();
        if (buf.readableBytes() < len) {
            buf.resetReaderIndex(); // 长度不够,复位读指针,等消息体完整
            return;
        }

        byte[] bytes = new byte[len];
        buf.readBytes(bytes);

        Msg msg = null;
        msg = (Msg) Class.forName("com.fan.tank.net.msg." + msgType.toString() + "Msg").getConstructor().newInstance();

        msg.parse(bytes);
        list.add(msg);
    }

    public static void main(String[] args) throws Exception {
        Msg msg = null;
        msg = (Msg) Class.forName("com.fan.tank.net.msg." + MsgType.TankDie + "Msg").getConstructor().newInstance();
        System.out.println(msg);
    }
}*/

public class MsgDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        ByteBuf buf = packet.copy().content();
//        if(buf.readableBytes() < 8) return;

        MsgType msgType = MsgType.values()[buf.readInt()];

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        Msg msg = null;
        msg = (Msg) Class.forName("com.fan.tank.net.msg." + msgType.toString() + "Msg").getConstructor().newInstance();

        msg.parse(bytes);
        out.add(msg);
    }
}
