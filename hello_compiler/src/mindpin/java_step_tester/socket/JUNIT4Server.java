package mindpin.java_step_tester.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import mindpin.java_step_tester.thread.ProcessRequestRunnable;

public class JUNIT4Server{
	private static final int DEFAULT_SERVER_PORT = 10001;
	
	private static int corePoolSize = 10;
	private static int maximumPoolSize = 10;
	private static int keepAliveTime = 0;
	private static int QueueCount = 200;
	
	public static void main(String[] args) throws IOException{
		read_thread_pool_config_from_config_file();
		
		int port = get_port();
		String message = "java_step_tester starting on 0.0.0.0:" + port + " ...";
		
		ServerSocket server = new ServerSocket(port);
		System.out.println(message); 
		
		ThreadPoolExecutor thread_pool = new ThreadPoolExecutor(  
				corePoolSize,       //corePoolSize 最小 线程数 
				maximumPoolSize,       //maximumPoolSize  最大线程数
				keepAliveTime,       //keepAliveTime  （最大线程 和 最小线程）之前线程的最大空闲时间
                TimeUnit.SECONDS,   // keepAliveTime 的单位 
                new ArrayBlockingQueue<Runnable>(QueueCount),  // 长度为 150 的等待队列  
                new RejectedExecutionHandler(){
					@Override
					public void rejectedExecution(Runnable r,
							ThreadPoolExecutor executor) {
						ProcessRequestRunnable runnable = (ProcessRequestRunnable)r;
						runnable.close();
					}
                }
                );
		
		while (true){
			Socket socket = null;
			try {
				socket = server.accept();
				ProcessRequestRunnable runnable = new ProcessRequestRunnable(socket);
				thread_pool.execute(runnable);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static int get_port() {
		String port_str = System.getProperty("java_step_tester.port");
		if(port_str == null || port_str.equals("")){
			return DEFAULT_SERVER_PORT;
		}else{
			return Integer.parseInt(port_str);
		}
	}
	
	private static void read_thread_pool_config_from_config_file(){
		try {   
			FileInputStream input_stream = new FileInputStream(System.getProperty("user.dir") + File.separator + "thread_pool.properties");
			Properties p = new Properties();   
			p.load(input_stream);
			corePoolSize = Integer.parseInt(p.getProperty("corePoolSize"));
			maximumPoolSize = Integer.parseInt(p.getProperty("maximumPoolSize"));
			keepAliveTime = Integer.parseInt(p.getProperty("keepAliveTime"));
			QueueCount = Integer.parseInt(p.getProperty("QueueCount"));
		} catch (IOException e1) {   
			e1.printStackTrace();
		}
		
		String thread_pool_message = "thread_pool_config" + "\r\n" + 
									 "  corePoolSize : " + corePoolSize + "\r\n" + 
									 "  maximumPoolSize : " + maximumPoolSize + "\r\n" +
									 "  keepAliveTime : " + keepAliveTime + "\r\n" + 
									 "  QueueCount : " + QueueCount + "\r\n";
		System.out.println(thread_pool_message);
	}
	
	public static void log(String log){
		System.out.println(log);
	}
}