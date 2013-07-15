package com.compilation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import com.google.gson.Gson;
import com.junit4.Listener;
import com.modle.Assets;
import com.modle.ResponseModle;
import com.thread.CreateServerThread;
import com.utils.FileUtil;

public class RunCode {
	public static HashMap<String, Boolean> test_map;
	public static HashMap<String, String> test_map_doc;
	public static  HashMap<String, Failure> test_map_error;
	
	public static String load_code(String input, String rule){
		// 1.创建需要动态编译的代码字符串
		String nr = "\r\n"; //回车
		return 
				
				" import com.compilation.MyInterface;" + nr +
				" import com.junit4.TestDescription;" + nr +
				
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
					 rule + nr +
				" }" + nr +
				
				" class RuleTest{" + nr + 
					input + nr +
				" }";
	}
	public static String return_json(ResponseModle responseModle){
		Gson gson = new Gson();
		String responseModle_json = gson.toJson(responseModle);
		
		System.out.println(responseModle_json);
		return responseModle_json;
	}
	
	public static String run(String classPath,String fullClassName,String error){
		JUnitCore core = new org.junit.runner.JUnitCore();
		core.addListener(new Listener());
		int i = 0;
		List<Assets> assets_list = new ArrayList<Assets>();

		try {
			Class<?> clz = new MyClassLoader(classPath).loadClass(fullClassName);
			new MyClassLoader(classPath).loadClass("RuleTest");
			MyInterface myObj = (MyInterface) clz.newInstance();
//			Result result =
					core.run(myObj.getClass());
		}catch (Exception e) {
			i++;
//			e.printStackTrace();
			System.out.println("=================编译错误=====================");
			error += "代码编译异常";
			return return_json(new ResponseModle(error, false, assets_list));
		}
		
		
		Set<String> set = test_map.keySet();
		Iterator<String> it = set.iterator();
		
		while(it.hasNext()){
			String name = it.next();

			String doc = test_map_doc.get(name) == null ?  "":test_map_doc.get(name);
			
			boolean is_success = test_map.get(name);
			Failure failure = test_map_error.get(name);
			String exception = "";
			if (!is_success) {
				exception = failure.getException().getClass() == AssertionError.class ? "":failure.getException().toString();
			}
			
			i += is_success ?  0:1;
			
			Assets assets = new Assets(doc, is_success, exception);
			assets_list.add(assets);
		}

		boolean success = i==0;
		ResponseModle responseModle = new ResponseModle(error, success, assets_list);
		return return_json(responseModle);
	}
	
	
	public static String thread(String classPath,String input, String rule){
		test_map = new HashMap<String, Boolean>();
		test_map_doc = new HashMap<String, String>();
		test_map_error = new HashMap<String, Failure>();

		String fullClassName = "InputTest";
		String complilation_error = "";
		try {
			new MyClassCompiler(classPath,fullClassName, RunCode.load_code(input, rule)).compile();
		} catch (Exception e) {
			e.printStackTrace();
			complilation_error = e.getMessage();
		}
		String result = RunCode.run(classPath,fullClassName,complilation_error);
		FileUtil.delFolder(classPath);
		return result;
	}
	
}
