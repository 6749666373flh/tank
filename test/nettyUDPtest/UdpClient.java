package nettyUDPtest;

import com.fan.tank.gameObjects.Player;
import com.fan.tank.net.msg.Msg;
import com.fan.tank.net.msg.TankJoinMsg;
import com.fan.tank.util.Direction;
import com.fan.tank.util.Group;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class UdpClient {
    public static final int MessageReceived = 0x99;
    private static int scanPort = 2555;

    public UdpClient(int scanPort) {
        this.scanPort = scanPort;
    }


    private static class CLientHandler extends SimpleChannelInboundHandler<Msg> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            ctx.channel().remoteAddress().toString();
//            ctx.writeAndFlush()
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        Channel channel = null;

        new Thread(()->{
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioDatagramChannel.class)
                        .handler(new ChannelInitializer<NioDatagramChannel>() {
                            @Override
                            protected void initChannel(NioDatagramChannel channel) throws Exception {
                                channel.pipeline().addLast(new UdpMsgEncoder()).addLast(new UdpMsgDecoder()).addLast(new CLientHandler());
                            }
                        });

                Channel ch = b.bind(0).sync().channel();

//                ch.writeAndFlush(new DatagramPacket(
//                        Unpooled.copiedBuffer("来自客户端:南无本师释迦牟尼佛", CharsetUtil.UTF_8),
//                        new InetSocketAddress("127.0.0.1", scanPort))).sync();
//                ch.closeFuture().await();
//                Scanner scanner = new Scanner(System.in);
//                while (scanner.hasNextLine()) {
//                    ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(scanner.nextLine(), CharsetUtil.UTF_8),
//                            new InetSocketAddress("127.0.0.1", scanPort)));
//                }

                TankJoinMsg msg = new TankJoinMsg(new Player(100, 200, Direction.D, Group.GOOD));

                ch.writeAndFlush(msg);
                ch.closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }).start();


    }
}