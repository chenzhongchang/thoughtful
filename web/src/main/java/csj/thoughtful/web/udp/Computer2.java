package csj.thoughtful.web.udp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Computer2 {
	public static void main(String[] args) throws Exception {
		// 收信
		// 1.创建一个空的数据报包
		byte[] b = new byte[1024];
		DatagramPacket dp = new DatagramPacket(b, 0, b.length);

		// 2. 创建一个数据包套接字
		DatagramSocket ds = new DatagramSocket(1234);
		ds.receive(dp);// 等待接收数据
		System.out.println("数据已接收");

		int len = dp.getLength();// 接收的数据长度
		System.out.println("len:" + len);

		String msg = new String(b, 0, len);
		System.out.println("msg[" + msg + "]");

	}
}
