package com.group.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动作注解
 * @author lingdai
 * 2017-03-29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActionAnnotation {
    public enum Type {NO, LOGIN, LOGIN_GROUP}

    String name() default "";           //动作名称描述

    String group() default "";          //动作功能分组

    boolean log() default false;        //是否写日志

    Type check() default Type.NO; //验证权限的类型 NO: 不需要验证登录  LOGIN: 只验证登录 LOGIN_GROUP: 验证登录和请求动作

    boolean encrypt() default false;    //是否加密
    
    String[] params() default {};       //请求参数

    //限流操作
    int seconds() default 0;//时间范围，单位秒
    int maxCount() default  0;//时间范围内最多访问次数
}
