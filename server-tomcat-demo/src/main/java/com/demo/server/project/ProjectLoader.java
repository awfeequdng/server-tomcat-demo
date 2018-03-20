package com.demo.server.project;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.map.HashedMap;

import com.demo.server.BootStraper;
import com.demo.server.domain.ServletBean;
import com.demo.server.domain.ServletParamObject;
 
/**
 * 加载和初始化servlet
 * @author USER
 *
 */
public class ProjectLoader {
	
	private String project;
	
	private URLClassLoader urlClassLoader;
	
	public ProjectLoader(String project)throws Exception{
		this.project=project;
		//创建一个类加载器
		
		//告诉JVM  class文件和jar包在哪里
		List<URL> urls=new ArrayList<URL>();
		//class文件路径    D:\study\server-test\ServletDemo1\WEB-INF\classes
		URL classUrls = new URL("file:"+BootStraper.work_space+"\\"+project+"\\WEB-INF\\classes\\");
		urls.add(classUrls);
		
		// 第三方  jar包路径   D:\study\server-test\ServletDemo1\WEB-INF\lib
		File libFile=new File(BootStraper.work_space+"\\"+project+"WEB-INF\\lib");
		if(libFile.exists()){
			for(String jarname:libFile.list()){
				urls.add(new URL("file:"+BootStraper.work_space+"\\"+project+"\\WEB-INF\\lib\\"+jarname));
			}
		}
		
		urlClassLoader = new URLClassLoader(urls.toArray(new URL[]{}),ProjectLoader.class.getClassLoader());
	}

	/**
	 * 真正的加载动作
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ServletException 
	 */
	public ProjectLoader load() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException{

		//接下来，告诉JVM 我要自己加载？ 用自己定义的类加载器加载
		Thread.currentThread().setContextClassLoader(urlClassLoader);
		//获取项目中的servlet，并实例化  初始化
		//因为项目的servlet 已经解析封住到 ProjectConfigBean中了
		final ProjectConfigBean projectConfigBean = BootStraper.projectConfigBeans.get(project);
		//获取说所有的servlet
		List<ServletBean> servlets=projectConfigBean.getServletBeans();
		
		for(final ServletBean servletBean:servlets){
			// <servlet-name>firstServlet</servlet-name>
		    //  <servlet-class>web.FirstServlet</servlet-class>
			//加载  servlet 
			final String servletName=servletBean.getServletName();
			String servletValue=servletBean.getServletClass();
			Class<?> clazz = urlClassLoader.loadClass(servletValue);
			//通过反射生成实例
			final Servlet servlet=(Servlet)clazz.newInstance();
			//保存项目的servlet实例
			projectConfigBean.getServletInstances().put(servletName,servlet);
			//处理servlet 参数
			
			
			//初始化servlet  init
			servlet.init(new ServletConfig() {
				List<ServletParamObject> initParamObjects=new ArrayList<ServletParamObject>(servletBean.getServletParam());
				Map<Object, Object> paraMap=new BeanMap(initParamObjects);
				
				@Override
				public String getServletName() {
					return servletName;
				}
				
				@Override
				public ServletContext getServletContext() {
					return new ServletContext() {
						
						@Override
						public void setAttribute(String name, Object object) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void removeAttribute(String name) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void log(String message, Throwable throwable) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void log(Exception exception, String msg) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void log(String msg) {
							System.out.println("系统日志："+msg);
						}
						
						@Override
						public Enumeration getServlets() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public Enumeration getServletNames() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public String getServletContextName() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public Servlet getServlet(String name) throws ServletException {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public String getServerInfo() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public Set getResourcePaths(String path) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public InputStream getResourceAsStream(String path) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public URL getResource(String path) throws MalformedURLException {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public RequestDispatcher getRequestDispatcher(String path) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public String getRealPath(String path) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public RequestDispatcher getNamedDispatcher(String name) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public int getMinorVersion() {
							// TODO Auto-generated method stub
							return 0;
						}
						
						@Override
						public String getMimeType(String file) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public int getMajorVersion() {
							// TODO Auto-generated method stub
							return 0;
						}
						
						@Override
						public Enumeration getInitParameterNames() {
							// 返回servlet 初始化参数列表
							Vector<Object> params=new Vector<Object>();
							Set<Object> keySet = paraMap.keySet();
							params.addAll(keySet);
							return params.elements();
						}
						
						@Override
						public String getInitParameter(String name) {
							return (String) paraMap.get(name);
						}
						
						@Override
						public String getContextPath() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public ServletContext getContext(String uripath) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public Enumeration getAttributeNames() {
							// 返回servlet 初始化参数列表
							Vector<Object> params=new Vector<Object>();
							Set<Object> keySet = paraMap.keySet();
							params.addAll(keySet);
							return params.elements();
						}
						
						@Override
						public Object getAttribute(String name) {
							// TODO Auto-generated method stub
							return null;
						}
					};
				}
				
				@Override
				public Enumeration getInitParameterNames() {
					// 返回servlet 初始化参数列表
					Vector<Object> params=new Vector<Object>();
					Set<Object> keySet = paraMap.keySet();
					params.addAll(keySet);
					return params.elements();
				}
				
				@Override
				public String getInitParameter(String name) {
					//根据参数名字，获取参数值
					return (String) paraMap.get(name);
				}
			});
			
		}
		return this;
	}
	
}
