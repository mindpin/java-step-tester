package com.compilation;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class MyClassCompiler {
	private String simpleClassName;
	private String code;
	private String classPath;

	public MyClassCompiler(String classPath,String simpleClassName, String code) {
		this.simpleClassName = simpleClassName;
		this.code = code;
		this.classPath = classPath;
	}

	public boolean compile() throws URISyntaxException{
		File file = new File(classPath);
		if (!file.exists()) {
			file.mkdir();
			System.out.println("编译时  file path ---------->   " + classPath);
		}
		
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject javaFile = new SimpleJavaFileObject(new URI(
				simpleClassName + ".java"), Kind.SOURCE) {
			@Override
			public CharSequence getCharContent(boolean arg){
				return code;
			}
		};
		
		CompilationTask task = compiler.getTask(null, null, null,
				Arrays.asList("-d", classPath), null,
				Arrays.asList(javaFile));
		return task.call();
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
}
