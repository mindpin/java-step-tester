package com.socket;

import com.compilation.RunCode;


public class SocketThead extends Thread  {
	public String input;
	public String rule;
	public String result;public static String classPath;
	
    public SocketThead(String input,String rule){
    	this.input = input;
    	this.rule = rule;
    }
    
    public void run() {
    	long threadId = Thread.currentThread().getId();
    	System.out.println("threadName : " + Thread.currentThread().getName() + " ： " + Thread.currentThread().getId());
    	result = RunCode.thread(classPath,input, rule);
    	System.out.println("run() : " + result);
    }
}
