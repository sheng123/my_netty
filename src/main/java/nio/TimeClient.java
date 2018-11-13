package nio;

/**
 * Created by sheng on 2018/11/8.
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 6666;

        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
