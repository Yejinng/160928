package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import chat.ChatClient;

public class ChatClientThread extends Thread {

	private Socket socket;
	private BufferedReader bufferedReader;

	public ChatClientThread(Socket socket, BufferedReader bufferedReader) {
		this.socket = socket;
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String data = bufferedReader.readLine();
				if(data == null){
					ChatClient.consoleLog("연결 끈낌");
					break;
				}
				System.out.println(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
