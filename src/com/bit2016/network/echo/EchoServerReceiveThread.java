package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;

	}

	@Override
	public void run() {
		try {
			InetSocketAddress isa = (InetSocketAddress)socket.getRemoteSocketAddress();
			System.out.println("[서버"+ getId() +"] 연결됨 [" + isa.getAddress().getHostAddress() + ":" + isa.getPort() + "]");
			
			// 4. IOStream 받아오기

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			while (true) {
				// 5.데이터읽기
				String data = br.readLine();
				if (data == null) {
					System.out.println("[서버] 클라이언트로부터 연결 끊김");
					break;
				}

				System.out.println("[서버] 데이터 수신:" + data);
				// 6. 쓰기
				pw.println(data);
				// pw.print(data + "\n");

			}
		} catch (SocketException e) {
			System.out.println("[서버] 클라이언트로부터 연결 끊김");
		} catch (IOException e) {
			System.out.println("여기");
		} finally {
			try{
				if(socket != null && socket.isClosed() == false){
					socket.close();	
				}
			} catch (IOException e){
				System.out.println("fdf");
			}
			
		}

	}
}
