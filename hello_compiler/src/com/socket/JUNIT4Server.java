package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import com.compilation.RunCode;


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
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private String createMessage(String line) throws IOException, JSONException, ClassNotFoundException, InstantiationException, IllegalAccessException{
			JSONObject jsonObject = new JSONObject(line);
			String input = jsonObject.getString("input");
			String rule = jsonObject.getString("rule");
			
			System.out.println("input : " + input);
			System.out.println("rule  : " + rule);
			
			
			long threadId = Thread.currentThread().getId();
	    	System.out.println("threadName : " + Thread.currentThread().getName() + " ： " + Thread.currentThread().getId());
	    	String  result = RunCode.thread(threadId,input, rule);
	    
			return "serveice junit4 : "+ result;
		}
	}
	
	public static void main(String[] args) throws IOException{
		new JUNIT4Server();
	}
}