package com.chub.eurekacommon.model;

public class ErrorCode {
	
	private String code;
	private String message;
	
	public ErrorCode(String code, String msg) {
		this.code = code;
		this.message = msg;
	}

	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
}
