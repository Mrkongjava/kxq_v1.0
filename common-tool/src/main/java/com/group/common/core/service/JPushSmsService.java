package com.group.common.core.service;

/**
 * 极光短信
 */
public interface JPushSmsService {

    public static String url = "https://api.sms.jpush.cn/v1/codes";

    /**
     * 发送文本短信
     * @param mobile
     * @param temp_id
     */
    String sendSMSCode(String mobile, int temp_id) throws Exception;

    /**
     * 验证短信
     * @param msgId
     */
    boolean sendValidSMSCode(String msgId, String code) throws Exception;
}
