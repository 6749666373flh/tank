package com.fan.tank.net;

import com.fan.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;


import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class Server {

//    public ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public void serverStart() {
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    //.option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel channel) throws Exception {
                            channel.pipeline().addLast(new MyServerHandler());
                        }
                    });

            ChannelFuture future = b.bind(8888).sync();
            System.out.println("---server start---");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    private class MyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        Set<InetSocketAddress> clients = new HashSet<>();

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
            InetSocketAddress sender = packet.sender();
            System.out.println(sender);
            clients.add(sender);
            clients.forEach(inetSocketAddress -> {
                if (inetSocketAddress != sender) {
                    ByteBuf buf = Unpooled.copiedBuffer(packet.copy().content());
                    //ReferenceCountUtil.retain(buf);
                    ctx.channel().writeAndFlush(new DatagramPacket(buf, inetSocketAddress));
                    System.out.println("转发完成");
                }
            });
        }

    }


}
