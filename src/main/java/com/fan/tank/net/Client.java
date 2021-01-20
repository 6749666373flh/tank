package com.fan.tank.net;

import com.fan.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

public class Client {

    public static final Client INSTANCE = new Client();

    private Client() { }

    private Channel channel = null;

    public void connect() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        try {
            boot.group(workerGroup)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new MyClientHandler());
                            channel = socketChannel;
                        }
                    })
                    .channel(NioSocketChannel.class);

            ChannelFuture future = boot.connect("localhost", 8888).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(TankJoinMsg msg) {
        channel.writeAndFlush(msg);
    }

    private class MyClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
        }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg tankJoinMsg) throws Exception {
            System.out.println(tankJoinMsg);
            tankJoinMsg.handle();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
