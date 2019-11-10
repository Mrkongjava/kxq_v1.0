package com.group.common.core.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 添加拦截器
 * @author lingdai
 * 2017-03-29
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInterceptor()).addPathPatterns("/**");  //对来自/user/** 这个链接来的请求进行拦截
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
	}
}
