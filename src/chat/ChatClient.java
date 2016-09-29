package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import com.bit2016.network.chat.ChatClientThread;

public class ChatClient {
	private static final String SERVER_IP = "192.168.1.6";
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) {
			Socket socket = null;
		try {	
			// socket
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("연결!");
			// reader/writer
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			// join protocol
			Scanner sc = new Scanner(System.in);
			System.out.print("닉네임을 입력하세요>>");
			String ninkname = sc.nextLine();
			pr.println("JOIN:" + ninkname);
			pr.flush();
			// keyboard 입력
			if (br.readLine().equals("JOIN : OK")) {
				Thread thread = new ChatClientThread(socket, br);
				thread.start();

				while (true) {
					String message = sc.nextLine();
					if (message.toLowerCase().equals("quit")) {
						pr.println("QUIT:" + message);
						break;
					} else {
						pr.println("MESSAGE:" + message);
					}
				}
			}
			if(sc != null){
				
			}	sc.close();
		} catch(SocketException e){
			consoleLog("eror:"+e);
		}catch (IOException e) {
			consoleLog("error:" + e);
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				consoleLog("error:" + e);
			}
		}

	}

	public static void consoleLog(String message) {
		System.out.println("[chat server]" + message);
	}

}
