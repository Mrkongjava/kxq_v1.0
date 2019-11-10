package com.group.common.core.service.impl;

import cn.jsms.api.JSMSClient;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.model.SMSPayload;
import com.group.common.core.service.JPushSmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 极光短信服务接口
 */
@Service
public class JPushSmsServiceImpl implements JPushSmsService {

    @Value("${masterSecret}")
    private String masterSecret;
    @Value("${appKey}")
    private String appKey;

    private JSMSClient jsmsClient = null;

    @Override
    public String sendSMSCode(String mobile, int temp_id) throws Exception{
        if(jsmsClient==null){
            jsmsClient = new JSMSClient(masterSecret, appKey);
        }
        SMSPayload smsPayload = SMSPayload.newBuilder()
                                    .setMobileNumber(mobile)
                                    .setTempId(temp_id).setTTL(120)
                                    .build();

        SendSMSResult result = jsmsClient.sendSMSCode(smsPayload);
        String messageId = result.getMessageId();
        return messageId;
    }

    @Override
    public boolean sendValidSMSCode(String msgId, String code) throws Exception{
        if(jsmsClient==null){
            jsmsClient = new JSMSClient(masterSecret, appKey);
        }
        try {
            ValidSMSResult result = jsmsClient.sendValidSMSCode(msgId, code);
            boolean is_valid = result.getIsValid();
            return is_valid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
