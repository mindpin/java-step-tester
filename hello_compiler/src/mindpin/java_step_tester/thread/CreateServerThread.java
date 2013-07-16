package mindpin.java_step_tester.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import mindpin.java_step_tester.compilation.RunCode;
import mindpin.java_step_tester.socket.JUNIT4Server;

import org.json.JSONException;
import org.json.JSONObject;

//--- CreateServerThread
public class CreateServerThread extends Thread{
	
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private StringBuffer request_log;
	
	public CreateServerThread(Socket socket){
		client = socket;
		this.request_log = new StringBuffer();
	}
	
	public void record_log(String log){
		if(this.request_log.length() != 0){
			this.request_log.append("\r\n");
		}
		this.request_log.append(log);
	}
	
	public void print_log(){
		System.out.println(this.request_log.toString());
	}
	
	public void run(){
		
		try{
			JUNIT4Server.log("处理请求:  " + client.getInetAddress().toString() + " on " + Thread.currentThread().getId());
			in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF8"));
			out = new PrintWriter(client.getOutputStream(), true);
			String line = in.readLine();
			while (!line.equals("bye")){
				JUNIT4Server.log("request:  " + line);
				
				String msg = createMessage(line);
				JUNIT4Server.log("response:  " + msg);
				out.println(msg);
				line = in.readLine();
			}
			out.println("--- See you, bye! ---");
			JUNIT4Server.log("请求结束  on " + Thread.currentThread().getId());
			print_log();
		}catch (IOException e){
			out.println(e.getMessage());
		}finally{
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private String createMessage(String line){
		
		JSONObject jsonObject;
		String input = "";
		String rule = "";
		try {
			jsonObject = new JSONObject(line);
			input = jsonObject.getString("input");
			rule = jsonObject.getString("rule");
		} catch (JSONException e) {
			return e.getMessage();
		}
		JUNIT4Server.log("input : " + input);
		JUNIT4Server.log("rule  : " + rule);
		
		return new RunCode(input, rule).get_result();
	}
}