package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by sheng on 2018/11/10.
 */
public class BioServerHandle implements Runnable {
    private Socket socket;

    public BioServerHandle(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("客户端连接到服务端");
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(this.socket.getOutputStream(), true);

            while (true) {
                String line = bufferedReader.readLine();
                if ("exit".equals(line)) {
                    printWriter.println("exit");
                    System.out.println("接收到 exit ，关闭连接");
                    break;
                } else if (line == null) {
                    break;
                }else {
                    System.out.println("客户端发送的数据为：" + line);
                    printWriter.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (printWriter != null) {
                printWriter.close();
            }

            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
