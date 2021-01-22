package nettyUDP;


/**
* @author andychen https://blog.51cto.com/14815984
* @description：广播运行器
*/
public class BroadcastRunner {
   /**
    * 运行消息广播
    * @param args
    */
   public static void main(String[] args) {
       NoticeBroadcast broadcast = null;
       try {
           broadcast = new NoticeBroadcast(Constant.ACCEPTER_PORT);
           broadcast.run();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           broadcast.stop();
       }
   }
}