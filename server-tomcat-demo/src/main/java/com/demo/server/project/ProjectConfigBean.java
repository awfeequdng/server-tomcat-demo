package com.demo.server.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.demo.server.BootStraper;
import com.demo.server.common.CommonConstances;
import com.demo.server.domain.ServletBean;
import com.demo.server.domain.ServletParamObject;
import com.demo.server.utils.StringUtils;

/**
 * 存储每个项目的配置文件的解析对象
 * @author USER
 *
 */   
public class ProjectConfigBean extends DefaultHandler {

	/*servlet name----->servlet class */
//	Map<String, Object> servlets=new HashMap<String, Object >();
	
	/*要是用servlet,的有servlet对象吧*/
	Map<String, Object> servletInstances=new HashMap<String, Object>();
	
	/*servlet  参数列表*/
//	Map<String, Map<String, String>> servletParam=new HashMap<String, Map<String,String>>();
	/*url map 映射*/
//	Map<String, String> servletMapping=new HashMap<String, String>();
	
	/*项目project*/
	private String project;
	
	/*xml文件地址*/
    private String xmlPath;
    
    private List<ServletBean> servletBeans=new ArrayList<ServletBean>();
	private ServletBean tempServletBean;
	private List<ServletParamObject> tempServletParam;
    private ServletParamObject tempServletParamObject;
    
	public List<ServletBean> getServletBeans() {
		return servletBeans;
	}

	public void setServletBeans(List<ServletBean> servletBeans) {
		this.servletBeans = servletBeans;
	}

	public ProjectConfigBean(String project) {
		super();
		this.project = project;
	}

	/**
	 * 解析 web.xml
	 * 读取 context_param   listener   filter  servlet
	 * 该实例只解析servlet
	 * 
	 */
	public ProjectConfigBean loadXml()throws Exception{
		this.xmlPath=BootStraper.work_space+"\\"+project+"\\WEB-INF\\web.xml";
		//创建解析工厂。
        SAXParserFactory mySaxF = SAXParserFactory.newInstance();
        //得到解析器。
        SAXParser myParser=mySaxF.newSAXParser();
        //读取xml内容;
        myParser.parse(xmlPath,this);
		return this;
	}
	
//	String currentServlet=null;
	//标签名
	String qName=null;

	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始解析xml:"+xmlPath);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.qName=qName;
		
		if(qName.equals(CommonConstances.SERVLET_BEAN)){
			 tempServletBean=new ServletBean();
			 tempServletParam=new ArrayList<ServletParamObject>();
		}
       
	}

	@Override
	public void endElement(String uri, String localName, String qName) 
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		
		if(qName.equals(CommonConstances.SERVLET_MAPPING)){
			tempServletBean.setServletParam(tempServletParam);
			servletBeans.add(tempServletBean);
			tempServletBean=null;
			tempServletParam=null;
			tempServletParamObject=null;
		}
		
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	    String currentValue=new String(ch, start, length);
	   
	    if(StringUtils.isEmpty(currentValue) || StringUtils.isEmpty(qName) || currentValue.contains("\n")){
	    	return;
	    }
	    
	    if(CommonConstances.SERVLET_NAME.equals(qName)){
	    	tempServletBean.setServletName(currentValue);
	    }else if(CommonConstances.SERVLET_CLASS.equals(qName)){
	    	tempServletBean.setServletClass(currentValue);
	    }else if(CommonConstances.PARAM_NAME.equals(qName)){
	    	tempServletParamObject=new ServletParamObject();
	    	tempServletParamObject.setParam_name(currentValue);
	    }else if(CommonConstances.PARAM_VALUE.equals(qName)){
	        tempServletParamObject.setParam_value(currentValue);
	        tempServletParam.add(tempServletParamObject);
	    }else if(CommonConstances.URL_PATTERN.equals(qName)){
	    	tempServletBean.setUrlParttern(currentValue);
	    }
	}

	public Map<String, Object> getServletInstances() {
		return servletInstances;
	}

	public void setServletInstances(Map<String, Object> servletInstances) {
		this.servletInstances = servletInstances;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
}
