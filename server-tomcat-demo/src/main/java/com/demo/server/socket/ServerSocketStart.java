package com.demo.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;

import com.demo.server.BootStraper;
import com.demo.server.domain.ServletBean;
import com.demo.server.project.ProjectConfigBean;

public class ServerSocketStart {
	
	public static final int SERVER_PORT=9999;
	public static final String SERVER_HOST="host";
	
	ServerSocket serverSocket;
	
	//创建线程池去处理客户端请求
	ExecutorService EXECUTOR_SERVICE=Executors.newCachedThreadPool();
	
/**
 * 服务器启动
 */
	public void start(){
		System.out.println("服务器开始启动");
		try {
			serverSocket=new ServerSocket(SERVER_PORT);
			System.out.println("服务器启动成功，端口："+SERVER_PORT);
			
			//准备接受请求
			//服务器，所以要一直在线，
//			while(true)
			for(;;)
	 		{
	            final Socket socket=serverSocket.accept();			
	            //客户端请求丢给线程池处理
	            EXECUTOR_SERVICE.execute(new Runnable() {
					
					public void run() {
					    try {
							byte[] requestBody=new byte[1024];
							socket.getInputStream().read(requestBody);
							String reqeuestr=new String(requestBody);
							System.out.println("收到客户端请求如下："+reqeuestr);
							
							//将http请求和 servlet 建立一个连接
							
							HttpServletRequest request=new RequestFactory().parseRequest(reqeuestr);
							HttpServletResponse response=new ResponseFactory().parseResponse(socket);
							//根据url请求，匹配的具体的servlet
							String project=request.getContextPath().split("/")[1];
							ProjectConfigBean projectConfigBean=BootStraper.projectConfigBeans.get(project);
							List<ServletBean> servletBeans=projectConfigBean.getServletBeans();
							Map<String, String> servletMapping = new HashedMap();
							for(ServletBean servletBean:servletBeans){
								servletMapping.put(servletBean.getUrlParttern(),servletBean.getServletName());
							}
							String servletName=servletMapping.get("/");
							if(null==servletName){
								//  /为空，说明没有匹配到   /
								// 根据全路径去继续匹配servlet
								servletName=servletMapping.get(request.getServletPath());
							}
							Servlet servlet=(Servlet) projectConfigBean.getServletInstances().get(servletName);
							servlet.service(request, response);
							
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							try {
								socket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}	
					}
				});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
	}
	

}
