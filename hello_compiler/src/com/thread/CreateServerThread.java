package com.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.notification.Failure;

import com.compilation.RunCode;
import com.utils.FileUtil;

//--- CreateServerThread
public class CreateServerThread extends Thread{
	
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	public static String classPath;
	
	public CreateServerThread(Socket s) throws IOException{
		client = s;
		in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF8"));
		out = new PrintWriter(client.getOutputStream(), true);
		
		classPath = System.getProperty("user.dir") + File.separator + "myFolder";
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
		long threadId = Thread.currentThread().getId();
		System.out.println("--------------------------- "+threadId+" ---------------------------");
		System.out.println("input : " + input);
		System.out.println("rule  : " + rule);
    	String  result = RunCode.thread(classPath + threadId ,input, rule);
		return ""+ result;
	}
}