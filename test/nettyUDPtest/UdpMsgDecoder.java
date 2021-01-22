package nettyUDPtest;

import com.fan.tank.net.msg.Msg;
import com.fan.tank.net.msg.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class UdpMsgDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        ByteBuf buf = packet.content();
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
