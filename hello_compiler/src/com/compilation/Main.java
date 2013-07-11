package com.compilation;

import java.net.URISyntaxException;

public class Main {
	public static void main(String[] args) {
		String fullClassName = "MyObj";

		String code = 	"import com.compilation.MyInterface; public class MyObj implements MyInterface{public void sayHello(){System.out.println(666);}}";
		String code_2 = "import com.compilation.MyInterface; public class MyObj implements MyInterface{public void sayHello(){System.out.println(7777);}}";
		String code_3 = "import com.compilation.MyInterface; public class MyObj implements MyInterface{public void sayHello(){System.out.println(88888);}}";
		load(code,fullClassName);
		load(code_2,fullClassName);
		load(code_3,fullClassName);
	}

	private static void load(String code, String fullClassName) {
		try {
			new MyClassCompiler(1,fullClassName, code).compile();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			MyInterface myObj = (MyInterface) new MyClassLoader().loadClass(
					fullClassName).newInstance();
			myObj.sayHello();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
