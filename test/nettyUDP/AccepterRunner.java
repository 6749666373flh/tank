package nettyUDP;

/**
* @author andychen https://blog.51cto.com/14815984
* @description：消息接收器启动器
*/
public class AccepterRunner {
   /**
    * 运行通知接收任务
    * @param args
    */
   public static void main(String[] args) {
       NoticeAccepter accepter = null;
       try {
           accepter = new NoticeAccepter();
           accepter.run();
       } catch (Exception e) {
           e.printStackTrace();
       }finally {
           accepter.stop();
       }
   }
}