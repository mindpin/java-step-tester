package com.compilation;

import java.util.Iterator;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.junit4.Listener;

public class RunCode {
	public static String load_code(String input, String rule){
		// 1.创建需要动态编译的代码字符串
		String nr = "\r\n"; //回车
		return 
				
				" import com.compilation.MyInterface;" + nr +
				
				" import org.junit.Assert; " + nr +
				" import org.junit.Test; " + nr +
				" import org.junit.runner.RunWith; " + nr +
				" import org.junit.runners.JUnit4; " + nr +
				
				" import java.util.Iterator;" + nr +
				" import java.util.Set;" + nr +
				" import org.junit.runner.JUnitCore;" + nr +
				" import org.junit.runner.Result;" + nr +
				
				" @RunWith(JUnit4.class) " + nr +
				" public class  InputTest implements MyInterface{" + nr + 
					" public void sayHello(){System.out.println(666);}" + nr +
					" @Test " + nr +
					input + nr +
				" }" + nr +
				
				" class RuleTest{" + nr + 
					rule + nr +
				" }";
	}
	
	public static String run(String fullClassName){
		JUnitCore core = new org.junit.runner.JUnitCore();
		core.addListener(new Listener());
		String retu = "";
		try {
			Class clz = new MyClassLoader().loadClass(fullClassName);
			MyInterface myObj = (MyInterface) clz.newInstance();
//			myObj.sayHello();
			
			Result result = core.run(myObj.getClass());
			
			Set<String> set = Listener.test_map.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext()){
				String name = it.next();
				boolean is_success = Listener.test_map.get(name);
				String is_success_str = is_success ? "success" : "failure";
				retu += "{ success: " + name + "  : 　　" +  is_success_str + " }";
				System.out.println("test " + name + " : " + is_success_str );
			}
			System.out.println(result.wasSuccessful());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retu;
	}
	
	
	public static String thread(long threadId,String input, String rule){
		Thread.currentThread();
		String fullClassName = "InputTest";
		new MyClassCompiler(threadId, fullClassName, RunCode.load_code(input, rule)).compile();
		return RunCode.run(fullClassName);
	}
	
}
