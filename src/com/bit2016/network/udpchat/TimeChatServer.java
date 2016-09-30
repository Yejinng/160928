package com.bit2016.network.udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeChatServer {

	public static final int PORT = 6787;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1. socket
			socket = new DatagramSocket(PORT);
			while (true) {
				// 2.타임수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.print(">>" + message);
				// 3.타임송신
				SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = Format.format(new Date());
				System.out.println(data);
				byte[] sendData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
						receivePacket.getPort());
				socket.send(sendPacket);
//				System.out.println(data);
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
