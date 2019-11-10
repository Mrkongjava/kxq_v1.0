package com.group.common.core.exception;

import com.group.common.core.commons.SysCode;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -3520789356146146317L;
	
	private String errorCode;
	private String errorDesc;
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	
	public ServiceException(SysCode sysCode){
		super();
		this.errorCode = sysCode.getCode();
		this.errorDesc = sysCode.getMessage();
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
