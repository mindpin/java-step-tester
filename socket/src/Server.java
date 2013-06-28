import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.json.JSONException;
import org.json.JSONObject;
public class Server extends ServerSocket{
	
	private static final int SERVER_PORT = 10001;
	
	public Server() throws IOException{
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
			}
		}
		private String createMessage(String line) throws IOException, JSONException{
			System.out.println("line : " + line);
			JSONObject jsonObject = new JSONObject(line);
			String method = jsonObject.getString("method");
			String scess = jsonObject.getString("scess");
			System.out.println("method " + method);
			System.out.println("scess " + scess);
			
			// 1.创建需要动态编译的代码字符串
			String nr = "\r\n"; //回车
			String source = "package temp.com; " + nr +
					" public class  Hello{" + nr + 
						method + nr +
						" public static void main (String[] args){" + nr + 
//							" System.out.println(\"HelloWorld! 1  nihao \");" + nr +
							scess + nr +
						" }" + nr +
					" }";
			// 2.将欲动态编译的代码写入文件中 1.创建临时目录 2.写入临时文件目录
			File dir = new File(System.getProperty("user.dir") + "/temp"); //临时目录
			// 如果 \temp 不存在 就创建
			if (!dir.exists()) {
				dir.mkdir();
			}
			FileWriter writer = new FileWriter(new File(dir,"Hello.java"));
			writer.write(source);
			writer.flush();
			writer.close();
			
			// 3.取得当前系统的编译器
			JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
			// 4.获取一个文件管理器
			StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
			// 5.文件管理器根与文件连接起来
			Iterable it = javaFileManager.getJavaFileObjects(new File(dir,"Hello.java"));
			// 6.创建编译任务
			CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./temp"), null, it);
			// 7.执行编译
			task.call();
			javaFileManager.close();
			
			// 8.运行程序
			Runtime run = Runtime.getRuntime();
			Process process = run.exec("java -cp ./temp temp/com/Hello");
			InputStream in = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String info  = "";
			System.out.println("reader 1");
			String red = reader.readLine();
			System.out.println(red);
			System.out.println("reader 2");

			return "serveice : "+ red;
		}
	}
	
	public static void main(String[] args) throws IOException{
		new Server();
	}
}