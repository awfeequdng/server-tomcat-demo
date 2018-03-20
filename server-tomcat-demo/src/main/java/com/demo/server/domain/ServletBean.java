package com.demo.server.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletBean {
	
	private String servletName;
	private String servletClass;
	private String urlParttern;
	private List<ServletParamObject> servletParam;
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	public String getServletClass() {
		return servletClass;
	}
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}
	public String getUrlParttern() {
		return urlParttern;
	}
	public void setUrlParttern(String urlParttern) {
		this.urlParttern = urlParttern;
	}
	public List<ServletParamObject> getServletParam() {
		return servletParam;
	}
	public void setServletParam(List<ServletParamObject> servletParam) {
		this.servletParam = servletParam;
	}

}
