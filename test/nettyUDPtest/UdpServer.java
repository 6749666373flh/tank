package nettyUDPtest;

import com.fan.tank.net.msg.Msg;
import com.fan.tank.net.msg.MsgType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UdpServer {
    private static Channel channel;
    public static void main(String[] args) {
        try {
            Bootstrap b = new Bootstrap();
            EventLoopGroup group = new NioEventLoopGroup();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler());

            channel = b.bind(2555).channel();
            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        Set<InetSocketAddress> set = new HashSet<>();


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
//            byte[] req = new byte[buf.readableBytes()];
//            buf.readBytes(req);
//            String body = new String(req, "UTF-8");
//            System.out.println(body);//打印收到的信息
//            //向客户端发送消息
////            String json = "来自服务端: 南无阿弥陀佛";
//            String json = body;
//            // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
//            byte[] bytes = json.getBytes("UTF-8");
//
//            InetSocketAddress sender = packet.sender();
//            set.add(sender);
//
//            DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), sender);
//            System.out.println(packet.sender());
//            ctx.writeAndFlush(data);//向客户端发送消息
            InetSocketAddress sender = packet.sender();
            System.out.println(sender);
            set.add(sender);
            set.forEach(inetSocketAddress -> {
                if (inetSocketAddress != sender) {
                    ByteBuf buf = Unpooled.copiedBuffer(packet.copy().content());
                    //ReferenceCountUtil.retain(buf);
                    ctx.writeAndFlush(new DatagramPacket(buf, inetSocketAddress));
                }
            });

        }
    }
}