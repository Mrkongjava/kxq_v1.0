package com.group.common.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.common.core.service.RongCloudService;
import com.group.common.utils.CommonUtils;
import com.group.common.utils.HttpClientUtils;
import com.group.common.utils.Sha1Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RongCloudServiceImpl implements RongCloudService {

    @Value("${ry.appKey}")
    private String appKey;
    @Value("${ry.appSecret}")
    private String appSecret;

    @Override
    public String getToken(String userId, String name, String portraitUri) throws Exception{
        String nonce = CommonUtils.createRandom(true,5);
        String timestamp = System.currentTimeMillis()+"";
        String Signature = Sha1Util.SHA1Digest(appSecret+nonce+timestamp);

        String url = "http://api.cn.ronghub.com/user/getToken.json";

        Map<String,String> headers = new HashMap<String, String>();
        headers.put("App-Key", appKey);
        headers.put("Nonce",nonce);
        headers.put("Timestamp",timestamp);
        headers.put("Signature",Signature);

        Map<String,String> params = new HashMap<String, String>();
        params.put("userId",userId);
        params.put("name",name);
        params.put("portraitUri",portraitUri);

        String result = HttpClientUtils.sendPost(url,params,headers,"utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        String token = jsonObject.getString("token");
        if("200".equals(code)){
            return token;
        }else {
            //throw new ServiceException("2000","获取token失败");
            return "";
        }
    }

    @Override
    public void refresh(String userId, String name, String portraitUri) throws Exception{
        String nonce = CommonUtils.createRandom(true,5);
        String timestamp = System.currentTimeMillis()+"";
        String Signature = Sha1Util.SHA1Digest(appSecret+nonce+timestamp);

        String url = "http://api.cn.ronghub.com/user/refresh.json";

        Map<String,String> headers = new HashMap<String, String>();
        headers.put("App-Key", appKey);
        headers.put("Nonce",nonce);
        headers.put("Timestamp",timestamp);
        headers.put("Signature",Signature);

        Map<String,String> params = new HashMap<String, String>();
        params.put("userId",userId);
        params.put("name",name);
        params.put("portraitUri",portraitUri);

        String result = HttpClientUtils.sendPost(url,params,headers,"utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        String token = jsonObject.getString("token");
        if(!"200".equals(code)){
            //throw new ServiceException("2000","刷新用户信息失败");
        }
    }
}
