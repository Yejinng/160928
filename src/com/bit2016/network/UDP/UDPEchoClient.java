package com.bit2016.network.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP = "192.168.1.6";
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		Scanner sc = null;
		DatagramSocket socket = null;
		try {
			// 1. 키보드연결
			sc = new Scanner(System.in);
			// 2. 소켓생성
			socket = new DatagramSocket();

			while (true) {
				// 3.사용자 입력받음
				System.out.print(">>");
				String message = sc.nextLine();
				if (message == null || "".equals(message)) {
					continue; // 비어있을 경우 다시 위로 돌림
				}
				// 4. 메세지 전송
				byte[] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));

				socket.send(sendPacket);
				// 5. 메시지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);

				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println(">>" + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원정리(clean up)
			if(sc != null){
				sc.close();
			}
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
