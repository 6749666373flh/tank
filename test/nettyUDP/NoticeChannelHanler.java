package nettyUDP;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
* @author andychen https://blog.51cto.com/14815984
* @description：通知通道处理器
*/
public class NoticeChannelHanler extends SimpleChannelInboundHandler<Notice> {
   /**
    * 接收广播传递过来的报文
    * @param channelHandlerContext
    * @param notice
    * @throws Exception
    */
   @Override
   protected void channelRead0(ChannelHandlerContext channelHandlerContext, Notice notice) throws Exception {
       StringBuffer buffer = new StringBuffer();
       buffer.append("时间[");
       buffer.append(notice.getTime());
       buffer.append("]，广播源[");
       buffer.append(notice.getSource().toString());
       buffer.append("]=====[");
       buffer.append(notice.getId());
       buffer.append("]=====通知内容：");
       buffer.append(notice.getContent());
       //打印接收到的数据
       System.out.println(buffer.toString());
   }

   /**
    * 异常捕获
    * @param ctx 上下文
    * @param cause
    * @throws Exception 异常信息
    */
   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
   }
}