package nettyUDP;

import java.net.InetSocketAddress;

/**
* @author andychen https://blog.51cto.com/14815984
* @description：通知信息
*/
public class Notice {
   public int getId() {
       return id;
   }

   public long getTime() {
       return time;
   }

   public String getContent() {
       return content;
   }

   public InetSocketAddress getSource() {
       return source;
   }
   //通知id
   private final int id;
   //发送时间
   private final long time;
   //通知内容
   private final String content;
   //来源地址
   private final InetSocketAddress source;
   //分隔符
   public static final byte SEPARATOR = (byte) ':';
   public Notice(int id, String content, InetSocketAddress source) {
       this.id = id;
       this.content = content;
       this.source = source;
       this.time = System.currentTimeMillis();
   }
}