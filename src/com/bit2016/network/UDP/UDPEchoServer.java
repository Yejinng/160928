package com.bit2016.network.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoServer {
	public static final int PORT = 5776;
	private static final int BUFFER_SIZE = 1024;
 
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1.socket생성
			socket = new DatagramSocket(PORT);
			while (true) {
				// 2.데이터수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);

//				System.out.println("[server] 대기중 ");
				socket.receive(receivePacket); // blocking

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println("[server] received :" + message);

				// 3.데이터송신
				byte[] sendData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
						receivePacket.getPort());
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
