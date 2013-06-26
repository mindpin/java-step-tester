import java.io.*;
import java.net.*;
public class Client{
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	public Client(){
		try{
			socket = new Socket("127.0.0.1", 10000);

			BufferedReader line = new BufferedReader(new InputStreamReader(System.in,"UTF8"));
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
			
			String readline;
			readline=line.readLine();
			
			while(!readline.equals("bye")){
				out.println(readline);
				out.flush();
				System.out.println("Client:"+readline);
				System.out.println("Server:"+in.readLine());
				readline=line.readLine();
			}

			line.close();
			out.close();
			in.close();
			socket.close();
		 }catch (IOException e){}
	}
	public static void main(String[] args){
		new Client();
	}
}

//这个客户端连接到地址为xxx.xxx.xxx.xxx的服务器，端口为10000，并从键盘输入一行信息，发送到服务器，然后接受服务器的返回信息，最后结束会话。