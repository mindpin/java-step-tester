package mindpin.java_step_tester.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mindpin.java_step_tester.thread.CreateServerThread;



public class JUNIT4Server{
	
	private static final int SERVER_PORT = 10001;
	
	public static void main(String[] args) throws IOException{
		ServerSocket server = new ServerSocket(SERVER_PORT);
		
		try{
			while (true){
				Socket socket = server.accept();
				new CreateServerThread(socket).start();
			}
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			server.close();
		}
		
	}
}