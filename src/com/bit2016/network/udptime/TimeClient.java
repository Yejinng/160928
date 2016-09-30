package com.bit2016.network.udptime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeClient {
	private static final String SERVER_IP = "192.168.1.6";
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1. socket
			socket = new DatagramSocket();

				// 2. 요청 메시지전송
				String message = "";
				byte[] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, TimeServer.PORT));
				socket.send(sendPacket);
				// 3.타임수신
				SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = Format.format(new Date());
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);

				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println(data);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}

		}
	}

}
