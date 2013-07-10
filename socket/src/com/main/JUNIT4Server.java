package com.main;
import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.json.JSONException;
import org.json.JSONObject;

public class JUNIT4Server extends ServerSocket{
	
	private static final int SERVER_PORT = 10002;
	
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
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private String createMessage(String line) throws IOException, JSONException, ClassNotFoundException{
			System.out.println("line : " + line);
			JSONObject jsonObject = new JSONObject(line);
			String input = jsonObject.getString("input");
			String rule = jsonObject.getString("rule");
			System.out.println("method :" + input);
			System.out.println("scess  :" + rule);
			
			InputRule.rule(rule);
			InputRule.input(input);
			String  result = InputRule.run();
			
			return "serveice junit4 : "+ result;
		}
	}
	
	public static void main(String[] args) throws IOException{
		new JUNIT4Server();
	}
}