import java.io.IOException;
import java.net.*;

/**
 * UDP搜索者，用于搜索服务支持方
 */
public class UDPSearcher {
    public static void main(String[] args) throws IOException {
        System.out.println("Searcher启动");

//        作为一个搜索方，无需指定端口，让系统分配端口
        DatagramSocket ds = new DatagramSocket();

//        构建一份请求数据
        String requestData = "hello world";
        byte[] requestDataBytes = requestData.getBytes();
//        直接构建消息
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes, requestDataBytes.length);
//        本机端口20000
        requestPacket.setAddress(InetAddress.getLocalHost());
        requestPacket.setPort(20000);

        ds.send(requestPacket);
        System.out.println("发送成功");

//        构建一个接受实体
        final byte[] buffer = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buffer, buffer.length);

//        接收
        ds.receive(receivePack);

//        打印接收到的消息与发送者的信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLength = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLength);
        System.out.println("Searcher接收到消息:ip:" + ip + ",端口号:" + port + ",数据长度:" + dataLength + ",数据内容:" + data);


        System.out.println("Searcher结束");
        ds.close();
    }
}
