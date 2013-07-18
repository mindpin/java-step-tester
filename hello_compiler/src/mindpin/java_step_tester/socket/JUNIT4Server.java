package mindpin.java_step_tester.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import mindpin.java_step_tester.thread.ProcessRequestRunnable;

public class JUNIT4Server{
	
	private static final int DEFAULT_SERVER_PORT = 10001;
	
	public static void main(String[] args) throws IOException{
		int port = get_port();
		String message = "java_step_tester starting on 0.0.0.0:" + port + " ...";
		
		ServerSocket server = new ServerSocket(port);
		System.out.println(message);
		
		ThreadPoolExecutor thread_pool = new ThreadPoolExecutor(  
                10,       //corePoolSize 最小 线程数 
                50,       //maximumPoolSize  最大线程数
                60,       //keepAliveTime  （最大线程 和 最小线程）之前线程的最大空闲时间
                TimeUnit.SECONDS,   // keepAliveTime 的单位 
                new ArrayBlockingQueue<Runnable>(150),  // 长度为 150 的等待队列  
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
	
	public static void log(String log){
		System.out.println(log);
	}
}