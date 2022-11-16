package csj.thoughtful.web.udp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.html.ListView;

public class Frame extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = -1274694940373010968L;

	// 北
	private JLabel ipLbl = new JLabel("广播IP：");
	private JTextField ipTxt = new JTextField(10);
	private JLabel portLbl = new JLabel("端口：");
	private JTextField portTxt = new JTextField(10);
	private JLabel nameLbl = new JLabel("昵称：");
	private JTextField nameTxt = new JTextField(10);
	private JButton onoffBtn = new JButton("启动");

	// 中
	private JTextArea msgArea = new JTextArea();
	private JScrollPane jScrollPane = new JScrollPane(msgArea);

	// 南
	private JTextField msgTxt = new JTextField(15);
	private JButton sendBtn = new JButton("发送");

	@Override
	public void run() {
		try {
			// 创建一个空的数据报包
			byte[] b = new byte[1024];
			DatagramPacket dp = new DatagramPacket(b, b.length);

			// 创建一个数据包套接字，用于接收数据
			String port = portTxt.getText();
			DatagramSocket datagramSocket = new DatagramSocket(Integer
					.parseInt(port));
			int len = -1;// 每次接收的数据的长度
			String msg = null;
			while (true) {
				datagramSocket.receive(dp);// 等待
				len = dp.getLength();
				msg = new String(b, 0, len);
				if ("p1.Frame.exit".equals(msg)) {
					System.out.println("p1.Frame.exit");
					datagramSocket.close();
					break;
				}
				appendMsg(msg);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("启动".equals(actionCommand)) {
			doStart();
		} else if ("停止".equals(actionCommand)) {
			doStop();
		} else if ("发送".equals(actionCommand)) {
			doSend();
		}

	}

	private void doStop() {
		System.out.println("doStop");
		appendMsg("聊天室已关闭");
		onoffBtn.setText("启动");
		ipTxt.setEditable(true);
		portTxt.setEditable(true);
		nameTxt.setEditable(true);
		sendBtn.setEnabled(false);

		// 向自己发送一个停止命令
		try {
			String port = portTxt.getText();
			InetAddress address = InetAddress.getLocalHost();// 自己
			byte[] b = "p1.Frame.exit".getBytes();// 命令
			DatagramPacket dp = new DatagramPacket(b, b.length, address,
					Integer.parseInt(port));

			// 使用数据报套按字发送数据
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(dp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void doStart() {
		String ip = ipTxt.getText();
		if (null == ip || "".equals(ip.trim())) {
			JOptionPane.showMessageDialog(this, "ip地址不能为空");
			return;
		}
		String port = portTxt.getText();
		if (null == port || "".equals(port.trim())) {
			JOptionPane.showMessageDialog(this, "端口号不能为空");
			return;
		}
		String name = nameTxt.getText();
		if (null == name || "".equals(name.trim())) {
			JOptionPane.showMessageDialog(this, "昵称不能为空");
			return;
		}

		// 只读
		ipTxt.setEditable(false);
		portTxt.setEditable(false);
		nameTxt.setEditable(false);
		sendBtn.setEnabled(true);

		// 将开关按钮改为停止
		onoffBtn.setText("停止");

		// 启动线程接收消息
		new Thread(this).start();
		appendMsg("聊天室已启动");
	}

	private void doSend() {
		String ip = ipTxt.getText();
		String port = portTxt.getText();
		String name = nameTxt.getText();
		String msg = msgTxt.getText();
		if (null == msg || "".equals(msg.trim())) {
			JOptionPane.showMessageDialog(this, "消息不能为空");
			return;
		} // 校验通过

		try {
			msg = name + "说：" + msg;// 拼接昵称
			// this.appendMsg(msg);

			// 创建数据包
			InetAddress address = InetAddress.getByName(ip);
			byte[] b = msg.getBytes();
			DatagramPacket dp = new DatagramPacket(b, b.length, address,
					Integer.parseInt(port));

			// 使用数据报套按字发送数据
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(dp);

			msgTxt.setText("");// 清空消息
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Frame() {
		this.setTitle("聊天室-UDP");
		this.setSize(600, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		JPanel jp = (JPanel) getContentPane();
		jp.setLayout(new BorderLayout());

		// 北
		JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp1.add(ipLbl);
		jp1.add(ipTxt);
		jp1.add(portLbl);
		jp1.add(portTxt);
		jp1.add(nameLbl);
		jp1.add(nameTxt);
		jp1.add(onoffBtn);
		jp.add(jp1, BorderLayout.NORTH);

		// 中
		jp.add(jScrollPane, BorderLayout.CENTER);

		// 南
		JPanel jp2 = new JPanel();
		jp2.setLayout(new BorderLayout());
		jp2.add(msgTxt, BorderLayout.CENTER);
		jp2.add(sendBtn, BorderLayout.EAST);
		jp.add(jp2, BorderLayout.SOUTH);
		sendBtn.setEnabled(false);// 禁用

		// 按钮事件处理
		onoffBtn.addActionListener(this);
		sendBtn.addActionListener(this);
		this.setVisible(true);
	}

	private void appendMsg(String msg) {
		msgArea.append(msg);
		msgArea.append("\r\n");// 换行
	}

	public static void main(String[] args) {
		Frame f = new Frame();


		DatagramPacket dp;
		DatagramSocket ds;

		//列表
	}
}
