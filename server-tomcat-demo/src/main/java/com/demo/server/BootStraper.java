package com.demo.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.demo.server.project.ProjectChecker;
import com.demo.server.project.ProjectConfigBean;
import com.demo.server.project.ProjectLoader;
import com.demo.server.socket.ServerSocketStart;

/**
 * Hello world!
 *
 */
public class BootStraper 
{
	//定义服务器的war路径
	public static String work_space="D://study//server-test";
	
	public static final Map<String, ProjectConfigBean> projectConfigBeans=new HashMap<String, ProjectConfigBean>();
	
    public static void main( String[] args )
    {
        System.out.println("服务器入口");
        
        
        try {
        	//发现项目，解压缩项目
			Set<String> projects = new ProjectChecker().checkout(work_space);
			
			for(String project:projects){
				ProjectConfigBean projectConfigBean = new ProjectConfigBean(project).loadXml();
				projectConfigBeans.put(project, projectConfigBean);
			}
			
			//加载初始化servlet
			for(String project:projects){
				new ProjectLoader(project).load();
			}
			
			//要对外提供服务吧，所以要写一个服务端socketServer
	        new ServerSocketStart().start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        
          
        
        
    }
}
