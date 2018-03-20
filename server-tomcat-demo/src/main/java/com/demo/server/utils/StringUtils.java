package com.demo.server.utils;

public class StringUtils {

	public static boolean isEmpty(String obj) {
		if(null==obj || "".equals(obj) || "null".equals(obj)){
			return true;
		}
		return false;
	}

}
