package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import com.compilation.MyClassCompiler;
import com.compilation.RunCode;
import com.utils.FileUtil;


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
			
		}finally{
			close();
		}
	}
	
	//--- CreateServerThread
	class CreateServerThread extends Thread{
		
		private Socket client;
		private BufferedReader in;
		private PrintWriter out;
		
		public CreateServerThread(Socket s) throws IOException{
			client = s;
			in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF8"));
			out = new PrintWriter(client.getOutputStream(), true);
			
			start();
		}
		public void run(){
			
			try{
				String line = in.readLine();
				while (!line.equals("bye")){
					String msg = createMessage(line);
					
					out.println(msg);
					line = in.readLine();
				}
				out.println("--- See you, bye! ---");
				client.close();
			}catch (IOException e){
				out.println(e.getMessage());
				try {
					client.close();
				} catch (IOException e1) {
					e1.printStackTrace();
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
			System.out.println("------------------------------------------------------");
//			System.out.println("line : " + line);
			System.out.println("input : " + input);
			System.out.println("rule  : " + rule);

			long threadId = Thread.currentThread().getId();
//	    	System.out.println("threadName : " + Thread.currentThread().getName() + " ： " + Thread.currentThread().getId());
	    	String  result = RunCode.thread(threadId,input, rule);
	    	FileUtil.delFolder(MyClassCompiler.classPath);
			return ""+ result;
		}
	}
	
	public static void main(String[] args) throws IOException{
		new JUNIT4Server();
	}
}