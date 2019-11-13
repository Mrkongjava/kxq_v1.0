package com.group.common.core.adapter;

import com.group.common.core.annotation.ActionAnnotation;
import com.group.common.core.commons.SysCode;
import com.group.common.core.config.MyWebAppConfig;
import com.group.common.core.exception.ServiceException;
import com.group.common.utils.SignUtils;
import com.group.common.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 签名拦截器
 * @author lingdai
 * 2017-03-29
 */
public class SignInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(SignInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String remortIP = getRemortIP(request);
        logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s, remortIP: %s", url, method, uri, queryString, remortIP));

        ActionAnnotation annotation = null;
        if (handler instanceof HandlerMethod) {
            //访问非静态资源
            Method methods = ((HandlerMethod)handler).getMethod();
            annotation = methods.getAnnotation(ActionAnnotation.class);
            if(annotation==null) {
                return true;
            }
        } else {
            //访问静态资源,不进行拦截
            return true;
        }

        boolean encrypt = annotation.encrypt();
        ActionAnnotation.Type type = annotation.check();
        String name = annotation.name();
        String[] paramKey = annotation.params();
        int seconds = annotation.seconds();
        int maxCount = annotation.maxCount();
        
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < paramKey.length; i++) {
            String param = request.getParameter(paramKey[i]);
            if(!"sign".equals(paramKey[i])){
                params.put(paramKey[i], param);
            }
            if (param != null) {
                logger.info("【text-- the key:" + paramKey[i] + " ,value :" + param + "】");
            } else {
                logger.info("【warn-- the key:" + paramKey[i] + " is empty or is null!】");
            }
        }

        //根据ip限流
        if (seconds != 0 && maxCount != 0){

            String requestKey = "";
            if (remortIP.indexOf(":")>0){
                requestKey = "current-limiting:" + Base64Util.encode(remortIP);
            }else {
                requestKey = "current-limiting:" + remortIP;
            }

            RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
            Object count = redisTemplate.opsForValue().get(requestKey);

            if(count  == null) {
                redisTemplate.opsForValue().set(requestKey,1,seconds, TimeUnit.SECONDS);
            }else if((int)count < maxCount) {
                redisTemplate.opsForValue().increment(requestKey);
            }else {
                throw new ServiceException("1001","系统繁忙，请稍后访问");
            }
        }



        //参数签名
        if(encrypt){
            String sign = request.getParameter("sign");
            if(StringUtils.isNotEmpty(sign)){
                String appSecert = MyWebAppConfig.environment.getProperty("ape.apikey");
                String ascstr = SignUtils.getSign(params, appSecert, logger);
                if(!sign.equals(ascstr)){
                    throw new ServiceException("2000","签名错误");
                }
            }else {
                throw new ServiceException("2000","签名不能为空");
            }
        }

        // 登录校验（用户H5的接口）
        if (type == ActionAnnotation.Type.LOGIN || type == ActionAnnotation.Type.LOGIN_GROUP){
            String token = request.getParameter("token");

            RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");
            Object loginUser = redisTemplate.opsForHash().get("user:token:"+token,"user");
            if(loginUser==null){
                throw new ServiceException(SysCode.NOT_LOGIN);
            }
        }

        request.setAttribute("params", params);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
