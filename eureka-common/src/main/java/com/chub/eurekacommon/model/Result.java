package com.chub.eurekacommon.model;

public class Result<T> implements java.io.Serializable {
	
	private static final long serialVersionUID = 4102259965593277454L;
	private boolean success;
	private String errorCode;
	private String errorMsg;
	private T data;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public void setError(String errCode, String errMsg) {
		this.setSuccess(false);
		this.setErrorCode(errCode);
		this.setErrorMsg(errMsg);
	}
	
	public void setError(ErrorCode err) {
		this.setSuccess(false);
		this.setError(err.getCode(), err.getMessage());
	}
	
	public String toErrorString(){
		return errorCode + ":" + errorMsg;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    public static <T> Result<T> createSuccessResult(){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> createSuccessResult(T data){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> createFailResult() {
    	Result<T> result = new Result<T>();
        result.setSuccess(false);
        return result;
    }
    
    public static <T> Result<T> createFailResult(ErrorCode err) {
    	Result<T> result = new Result<T>();
    	result.setError(err);
    	return result;
    }

    public static <T> Result<T> createFailResult(ErrorCode err, T data) {
        Result<T> result = new Result<T>();
        result.setError(err);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> createFailResult(String code, String msg){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setErrorCode(code);
        result.setErrorMsg(msg);
        return result;
    }

    public static <T> Result<T> createFailResult(String code, String msg, T data){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setErrorCode(code);
        result.setErrorMsg(msg);
        result.setData(data);
        return result;
    }
}
