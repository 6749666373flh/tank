package nettyUDP;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
* @author andychen https://blog.51cto.com/14815984
* @description：通知的广播端
*/
public class NoticeBroadcast {
   //广播线程组
   private final EventLoopGroup group;
   //广播启动器
   private final Bootstrap boot;

   /**
    * 默认构造
    * @param remotePort 接收端端口
    */
   public NoticeBroadcast(int remotePort) {
       this.group = new NioEventLoopGroup();
       this.boot = new Bootstrap();
       //绑定NioDatagramChannel数据报通道
       this.boot.group(group).channel(NioDatagramChannel.class)
       //设置通道用于广播
       .option(ChannelOption.SO_BROADCAST, true)
       .handler(new NoticeEncoder(new InetSocketAddress(Constant.BROADCAST_IP, remotePort)));
   }

   /**
    * 运行广播
    */
   public void run() throws Exception {
       int count = 0;
       //绑定广播通道
       Channel channel = this.boot.bind(0).sync().channel();
       System.out.println("开始运行广播，发送通知，目标所有主机端口("+Constant.ACCEPTER_PORT+")...");
       //循环广播通知
       for (;;){
           /**
            * 发送通知到接收端
            */
          channel.writeAndFlush(new Notice(++count, Constant.getNotice(),null));
          //间隔3秒发送
           try {
               Thread.sleep(3000);
           } catch (InterruptedException e) {
               Thread.interrupted();
               e.printStackTrace();
               break;
           }
       }
   }

   /**
    * 停止运行
    */
   public void stop(){
       try {
           this.group.shutdownGracefully();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}