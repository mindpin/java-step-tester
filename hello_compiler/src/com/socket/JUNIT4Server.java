package com.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.thread.CreateServerThread;


public class JUNIT4Server extends ServerSocket{
	
	private static final int SERVER_PORT = 10001;
	
	public JUNIT4Server() throws IOException{
		super(SERVER_PORT);
		
		try{
			while (true){
				Socket socket = accept();
				new CreateServerThread(socket);
			}
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}

	public static void main(String[] args) throws IOException{
		new JUNIT4Server();
	}
}