package com.group.common.core.service;

import java.util.Map;

/**
 * 微信公众号H5接口
 */
public interface WeixinGzhService {

    /**
     * 网页授权（非静默式授权，即scope=snsapi_base）
     * 1，refreshToken不为空则通过refreshToken获取access_token和用户信息
     * 2，refreshToken为空则通过code获取access_token和用户信息
     *
     * @param code
     * @param refreshToken
     * @param scope 作用域:snsapi_base经模式授权，snsapi_userinfo非静模式授权
     * @return
     * @throws Exception
     */
    Map<String, Object> oauth2(String code, String refreshToken, String scope) throws Exception;

    /**
     * 获取公众号access_token
     *
     * @return
     * @throws Exception
     */
    String getGzhAccessToken() throws Exception;

    /**
     * 获取access_token
     *
     * @param appid
     * @param secret
     * @return
     * @throws Exception
     */
    String getAccessToken(String appid, String secret) throws Exception;

    /**
     * 获取接口权限配置
     *
     * @param url
     * @return
     * @throws Exception
     */
    Map<String, Object> getJSSdkConfig(String url) throws Exception;

}
