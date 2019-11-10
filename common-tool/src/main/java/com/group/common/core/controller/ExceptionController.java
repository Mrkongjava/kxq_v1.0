package com.group.common.core.controller;

import com.group.common.core.commons.Body;
import com.group.common.core.commons.SysCode;
import com.group.common.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理controller
 * @author dailing
 * 
 */
@ControllerAdvice
public class ExceptionController extends BaseController{
	Logger logger = LoggerFactory.getLogger(HandlerExceptionResolver.class);
	
	@ExceptionHandler(ServiceException.class)
	public ModelAndView handleAllException(HttpServletRequest request,
                                           HttpServletResponse response, ServiceException e) {
		Body body = new Body();
		body.setCode(e.getErrorCode());
		body.setMessage(e.getErrorDesc());
		renderJson(request, response, body);
		logger.warn("业务异常-"+e.getErrorDesc());
		return null;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleAllException(HttpServletRequest request,
                                           HttpServletResponse response, IllegalArgumentException e) {
		Body body = new Body();
		body.setCode(SysCode.PARAM_IS_ERROR.getCode());
		body.setMessage(SysCode.PARAM_IS_ERROR.getMessage()+"-"+e.getMessage());
		//body.setResult(e.getMessage());
		renderJson(request, response, body);
		logger.warn("参数错误-"+e.getMessage());
		//e.printStackTrace();
		return null;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(HttpServletRequest request,
                                           HttpServletResponse response, Exception e) {
		Body body = new Body();
		body.setCode(SysCode.SYS_ERR.getCode());
		body.setMessage(SysCode.SYS_ERR.getMessage());
		body.setResult(e);
		renderJson(request, response, body);
		logger.error("操作错误-"+e.getMessage(),e);
		return null;
	}
	
}
