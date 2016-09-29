package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "169.254.119.50";
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		try {
			//1
			sc = new Scanner(System.in);
			//2
			socket = new Socket();
			//3
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			System.out.println("connect!!");
			//4
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
			//5
			System.out.print("nickname>>");
			String nickname = sc.nextLine();
			System.out.println(nickname);
			pw.println("JOIN:" + nickname);
			//6
			if(br.readLine().equals("JOIN:OK")){
				Thread thread = new ChatClientThread(socket, br);
				thread.start();
			//7
				while(true){
					System.out.print(">>");
					String input = sc.nextLine();
					
				if("quit".equals(input)==true){
					pw.println("QUIT:" + input);
					break;
				} else
					pw.println("MESSAGE:"+input);
				}	
			}
			sc.close();
		} catch(SocketException e){
			consoleLog("error1:" + e);
			
		} catch (IOException e) {
			consoleLog("error2:" + e);
		} finally {
			try{
				if(socket != null && socket.isClosed() ==false)	{
					socket.close();
				}
			} catch (IOException e){
				consoleLog("error3:" + e);
			}
		}
	}

	public static void consoleLog(String message) {
		System.out.println("[chat server]" + message);
	}
}
