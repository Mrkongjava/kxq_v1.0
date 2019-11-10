package com.group.common.core.service;

import java.util.Map;

/**
 * 阿里云短信接口
 */
public interface AliyunSmsService {

    /**
     * 发送短信验证码
     * @param tel
     * @param param
     * @param templateCode
     */
    public void sendSms(String tel, Map<String, Object> param, String templateCode) throws Exception;
}
