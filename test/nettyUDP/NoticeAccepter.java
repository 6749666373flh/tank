package nettyUDP;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
* @author andychen https://blog.51cto.com/14815984
* @description：通知接收器
*/
public class NoticeAccepter {
   //通知线程组
   private final EventLoopGroup group;
   //启动器
   private final Bootstrap boot;
   public NoticeAccepter() {
       this.group = new NioEventLoopGroup();
       this.boot = new Bootstrap();
       this.boot.group(this.group)
               .channel(NioDatagramChannel.class)
               //开启通道底层广播
               .option(ChannelOption.SO_BROADCAST, true)
               //端口重用
               .option(ChannelOption.SO_REUSEADDR, true)
               .handler(new ChannelInitializer<Channel>() {
                   @Override
                   protected void initChannel(Channel channel) throws Exception {
                       ChannelPipeline pipeline = channel.pipeline();
                       pipeline.addLast(new NoticeDecoder());
                       pipeline.addLast(new NoticeChannelHanler());
                   }
               })
               .localAddress(Constant.ACCEPTER_PORT);
   }

   /**
    * 运行接收器
    */
   public void run(){
       try {
           //设置不间断接收消息，并绑定通道
           Channel channel = this.boot.bind().syncUninterruptibly().channel();
           System.out.println("接收器启动，端口("+ Constant.ACCEPTER_PORT+")，等待接收通知...");
           //通道阻塞，直到关闭
           channel.closeFuture().sync();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }finally {
          this.stop();
       }
   }

   /**
    * 停止接收消息
    */
   public void stop(){
       try {
           this.group.shutdownGracefully();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}