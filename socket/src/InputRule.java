import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.unit4.com.Listener;
import com.unit4.com.PracticeTest;


public class InputRule {
	public static void input(String input) throws IOException{
		// 1.创建需要动态编译的代码字符串
		String nr = "\r\n"; //回车
		String source = 
				" package temp; " + nr +
				" import org.junit.Assert; " + nr +
				" import org.junit.Test; " + nr +
				" import org.junit.runner.RunWith; " + nr +
				" import org.junit.runners.JUnit4; " + nr +
				" @RunWith(JUnit4.class) " + nr +
				" public class  InputTest{" + nr + 
					" @Test " + nr +
					input + nr +
				" }";
		// 2.将欲动态编译的代码写入文件中 1.创建临时目录 2.写入临时文件目录
		File dir = new File(System.getProperty("user.dir") + "/temp"); //临时目录
		// 如果 \temp 不存在 就创建
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileWriter writer = new FileWriter(new File(dir,"InputTest.java"));
		writer.write(source);
		writer.flush();
		writer.close();
		
		// 3.取得当前系统的编译器
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		// 4.获取一个文件管理器
		StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		// 5.文件管理器根与文件连接起来
		Iterable it = javaFileManager.getJavaFileObjects(new File(dir,"InputTest.java"));
		// 6.创建编译任务
		CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./temp"), null, it);
		// 7.执行编译
		task.call();
		javaFileManager.close();
	}
	
	public static void rule(String rule) throws IOException{
		// 1.创建需要动态编译的代码字符串
		String nr = "\r\n"; //回车
		String source = "package temp; " + nr +
				" public class  RuleTest{" + nr + 
					rule + nr +
				" }";
		// 2.将欲动态编译的代码写入文件中 1.创建临时目录 2.写入临时文件目录
		File dir = new File(System.getProperty("user.dir") + "/temp"); //临时目录
		// 如果 \temp 不存在 就创建
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileWriter writer = new FileWriter(new File(dir,"RuleTest.java"));
		writer.write(source);
		writer.flush();
		writer.close();
		
		// 3.取得当前系统的编译器
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		// 4.获取一个文件管理器
		StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		// 5.文件管理器根与文件连接起来
		Iterable it = javaFileManager.getJavaFileObjects(new File(dir,"RuleTest.java"));
		// 6.创建编译任务
		CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./temp"), null, it);
		// 7.执行编译
		task.call();
		javaFileManager.close();
	}
	
	public static String run() throws IOException, ClassNotFoundException{
		JUnitCore core = new org.junit.runner.JUnitCore();
		core.addListener(new Listener());
		Class c = Class.forName("temp.InputTest");
		Result result = core.run(c);
		
		Set<String> set = Listener.test_map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String name = it.next();
			boolean is_success = Listener.test_map.get(name);
			String is_success_str = is_success ? "success" : "failure"; 
			System.out.println("test " + name + " : " + is_success_str);
		}
		
		System.out.println(result.wasSuccessful());
		
		return result.wasSuccessful()+"";
	}
}
