package com.demo.server.common;

public enum CommonResultCode {
	
	/*************************************************
     *              系统类型返回码                                                                      *
     *************************************************/
     /*** 成功 ***/
     SUCCESS(200,"成功"),
     /*** 系统异常 ***/
     SYSTEM_ERROR(500, "系统异常，请稍后重试"),
     /*** 数据不存在 ***/
     DATA_NOT_EXIST(600, "数据不存在"),
     /*** 数据已存在 ***/
     DATA_EXIST(601, "数据已存在"),
     /*** 参数为空 ***/
     PARAM_NULL(700, "参数为空"),
     /*** 参数错误 ***/
     PARAM_ERROR(701, "参数错误"),
     ;
     
     private int code;
 	private String message;
	private CommonResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
