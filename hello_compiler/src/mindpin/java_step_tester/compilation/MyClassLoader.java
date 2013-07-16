package mindpin.java_step_tester.compilation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import mindpin.java_step_tester.socket.JUNIT4Server;

public class MyClassLoader extends ClassLoader {
	
	private boolean alwaysDefineClass = true;
	private String classPath;
	
	public MyClassLoader(String classPath){
		this.classPath = classPath;
	}
	
	@Override
	protected Class<?> findClass(String fullClassName)
			throws ClassNotFoundException {
		Class<?> clazz = null;
		// clazz = findLoadedClass(fullClassName);
		// if(alwaysDefineClass || clazz == null){
		byte[] raw = readClassBytes(fullClassName);

		clazz = defineClass(fullClassName, raw, 0, raw.length);
		resolveClass(clazz);
		// }

		return clazz;
	}

	private byte[] readClassBytes(String fullClassName) {
		byte[] raw = null;
		InputStream stream = null;
		File file = new File(classPath + File.separator
				+ fullClassName.replaceAll("\\.", "/") + ".class");
		
		JUNIT4Server.log("运行时  file path ---------->   " + file.getAbsolutePath());
		
		try {
			stream = new FileInputStream(file);
			raw = new byte[(int) file.length()];
			stream.read(raw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return raw;
	}

	public boolean isAlwaysDefineClass() {
		return alwaysDefineClass;
	}

	public void setAlwaysDefineClass(boolean alwaysDefineClass) {
		this.alwaysDefineClass = alwaysDefineClass;
	}
}
