import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //    服务端接收消息
    public static void main(String[] args) throws IOException {
//        服务器端监听，如果没有设置端口号，则会在所有可用端口号上进行监听
        ServerSocket server = new ServerSocket(2000);

        System.out.println("服务器准备就绪");
        System.out.println("服务器信息:" + server.getInetAddress() + "Port:" + server.getLocalPort());


        for (; ; ) {
            //等待客户端连接
            Socket client = server.accept();
//            把客户端加入异步线程
            ClientHandler clientHandler = new ClientHandler(client);
//            异步线程启动
            clientHandler.start();
        }
    }

    /**
     * 客户端消息处理
     */
    private static class ClientHandler extends Thread {
        //        当前的连接
        private Socket socket;
        private boolean exit = false;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        //      重写run方法
        @Override
        public void run() {
            super.run();
//            打印客户端信息
            System.out.println("新客户端连接:" + socket.getInetAddress() + "Port:" + socket.getPort());

            try {
//                得到打印流，用于数据输出，服务器回送数据使用
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
//                输入流，用户接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do {
                    String str = socketInput.readLine();
                    if ("Bye".equalsIgnoreCase(str)) {
                        this.exit = true;
                        socketOutput.println("Bye");
                    } else {
                        //打印消息，并回送长度
                        System.out.println(str);
                        socketOutput.println("回送:消息长度" + str.length());
                    }
                } while (!exit);

                socketInput.close();
                socketInput.close();

            } catch (Exception e) {
                System.out.println("连接异常断开");
            } finally {
//                连接关闭
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("客户端已关闭" + socket.getInetAddress() + "Port:" + socket.getPort());
        }
    }
}
