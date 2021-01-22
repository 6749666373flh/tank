package nettyUDP;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
* @author andychen https://blog.51cto.com/14815984
* @description：通知编码器
*/
public class NoticeEncoder extends MessageToMessageEncoder<Notice> {
   //目的地
   private final InetSocketAddress target;

   public NoticeEncoder(InetSocketAddress target) {
       this.target = target;
   }

   /**
    * 编码方法实现
    * @param ctx 处理器上下文
    * @param notice 通知对象
    * @param list 集合
    * @throws Exception
    */
   protected void encode(ChannelHandlerContext ctx, Notice notice, List<Object> list) throws Exception {
       //内容数据
       byte[] bytes = notice.getContent().getBytes(CharsetUtil.UTF_8);
       //定义缓冲:一个int型+一个long型+内容长度+分隔符
       int capacity = 4+8+bytes.length+1;
       ByteBuf buf = ctx.alloc().buffer(capacity);
       //写通知id
       buf.writeInt(notice.getId());
       //发送时间
       buf.writeLong(notice.getTime());
       //分隔符
       buf.writeByte(Notice.SEPARATOR);
       //内容
       buf.writeBytes(bytes);
       //加入消息列表
       list.add(new DatagramPacket(buf, target));
   }
}