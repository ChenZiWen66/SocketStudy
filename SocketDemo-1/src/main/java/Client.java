import java.io.*;
import java.net.*;

public class Client {
    //    客户端可以发送数据（消息）
    public static void main(String[] args) throws IOException {
//    1.初始化一个socket
        Socket socket = new Socket();
//      设置超时时间为3000
        socket.setSoTimeout(3000);
//      设置连接：连接本地，端口号2000，超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);
        System.out.println("已发起服务器连接，并进入后续流程~");
        System.out.println("客户端信息:" + socket.getLocalAddress() + "Port:" + socket.getLocalPort());
        System.out.println("服务端信息" + socket.getInetAddress() + "Port:" + socket.getPort());

        try {
//            发送接收数据
            todo(socket);
        } catch (Exception e) {
            System.out.println("异常关闭");
        }
//        资源释放
        socket.close();
        System.out.println("客户端已退出~");
    }

    /**
     * @param client:传入一个已经连接好的socket
     */
    private static void todo(Socket client) throws IOException {
//        拿到键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

//        拿到客户端的输出流,并转换成打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

//        得到socket输入流,并转换成打印流
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean exit = false;
        do {
            //        从键盘读取一行
            String str = input.readLine();
//        发送至服务器
            socketPrintStream.println(str);

//        从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if ("Bye".equalsIgnoreCase(echo)) {
                exit = true;
            } else {
                System.out.println(echo);
            }
        } while (!exit);

//        结束后关闭所有socket流，资源释放
        inputStream.close();
        outputStream.close();
    }
}
