package mindpin.java_step_tester.compilation;

import java.io.File;
import mindpin.java_step_tester.junit4.Listener;
import mindpin.java_step_tester.modle.TestResult;
import mindpin.java_step_tester.utils.FileUtil;
import org.junit.runner.JUnitCore;

public class RunCode {
	public static String INPUT_CLASS_NAME = "InputTest";
	private String class_path;
	private String input;
	private String rule;
	private TestResult text_result;
	
	
	public RunCode(String input, String rule){
		long thread_id = Thread.currentThread().getId();
		this.class_path = System.getProperty("java.io.tmpdir") + File.separator + "dyncompiler" + thread_id;
		this.input = input;
		this.rule = rule;
	}
	
	public String get_result(){
		String result;
		boolean complie_success = compile();
		if(complie_success){
			result = run();
		}else{
			result = TestResult.COMPILE_ERROR_RESULT;
		}
		FileUtil.delFolder(this.class_path);
		return "" + result;
	}
	
	private String get_full_source_code(){
		// 1.创建需要动态编译的代码字符串
		String nr = "\r\n"; //回车
		return 
				
				" import mindpin.java_step_tester.junit4.TestDescription;" + nr +
				
				" import org.junit.Assert; " + nr +
				" import org.junit.Test; " + nr +
				" import org.junit.runner.RunWith; " + nr +
				" import org.junit.runners.JUnit4; " + nr +
				
				" import java.util.Iterator;" + nr +
				" import java.util.Set;" + nr +
				" import org.junit.runner.JUnitCore;" + nr +
				" import org.junit.runner.Result;" + nr +
				
				" @RunWith(JUnit4.class) " + nr +
				" public class " +  INPUT_CLASS_NAME + "{" + nr + 
					 this.rule + nr +
				" }" + nr +
				
				" class RuleTest{" + nr + 
					this.input + nr +
				" }";
	}
	
	private String run(){
		JUnitCore core = new org.junit.runner.JUnitCore();
		this.text_result = new TestResult();
		Listener listener = new Listener(text_result);
		core.addListener(listener);

		try {
			Class<?> clz = new MyClassLoader(this.class_path).loadClass(INPUT_CLASS_NAME);
			core.run(clz);
		}catch (Exception e) {
//			e.printStackTrace();
		}
		
		return text_result.to_json();
	}
	
	private boolean compile(){
		boolean success;
		String full_source_code = get_full_source_code();
		try {
			success = new MyClassCompiler(this.class_path, INPUT_CLASS_NAME, full_source_code).compile();
		} catch (Exception e) {
//			e.printStackTrace();
			success = false;
		}
		return success;
	}
	

	
}
