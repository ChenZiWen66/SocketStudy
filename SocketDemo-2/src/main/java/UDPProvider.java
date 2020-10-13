import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP提供者
 * 用于提供服务
 */
public class UDPProvider {
    public static void main(String[] args) throws IOException {
        System.out.println("Provider启动");

        //作为一个接受者，指定一个端口进行监听。用于数据接收。
        DatagramSocket ds = new DatagramSocket(20000);

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
        System.out.println("Provider接收到消息:ip:" + ip + ",端口号:" + port + ",数据长度:" + dataLength + ",数据内容:" + data);

//        构建一份回送数据
        String responseData = "接收到消息，长度为:"+dataLength;
        byte[] responseDataBytes = responseData.getBytes();
//        直接跟发送者构建一份回送消息
        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,responseDataBytes.length, receivePack.getAddress(),receivePack.getPort());

        ds.send(responsePacket);

        System.out.println("Provider结束");
        ds.close();
    }
}
