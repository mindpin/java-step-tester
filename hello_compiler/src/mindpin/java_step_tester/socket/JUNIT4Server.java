package mindpin.java_step_tester.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mindpin.java_step_tester.thread.CreateServerThread;



public class JUNIT4Server{
	
	private static final int DEFAULT_SERVER_PORT = 10001;
	
	public static void main(String[] args) throws IOException{
		int port = get_port();
		String message = "java_step_tester starting on 0.0.0.0:" + port + " ...";
		System.out.println(message);
		ServerSocket server = new ServerSocket(port);
		
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
	
	private static int get_port() {
		String port_str = System.getProperty("java_step_tester.port");
		if(port_str == null || port_str.equals("")){
			return DEFAULT_SERVER_PORT;
		}else{
			return Integer.getInteger(port_str);
		}
	}
}