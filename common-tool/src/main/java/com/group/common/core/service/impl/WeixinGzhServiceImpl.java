package com.group.common.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.common.core.exception.ServiceException;
import com.group.common.core.service.WeixinGzhService;
import com.group.common.utils.CommonUtils;
import com.group.common.utils.HttpClientUtils;
import com.group.common.utils.HttpUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinGzhServiceImpl implements WeixinGzhService {

    @Value("${gzh.appid}")
    private String gzh_appid;
    @Value("${gzh.secret}")
    private String gzh_secret;


    @Autowired
    private CacheManager cacheManager;

    @Override
    public Map<String, Object> oauth2(String code, String refreshToken,String scope) throws Exception{
        Map<String, Object> map = new HashedMap();

        if(StringUtils.isEmpty(code) && StringUtils.isEmpty(refreshToken)){
            throw new ServiceException("2000", "code或refreshToken参数错误");
        }

        Map<String, String> params = null;
        String access_token = null;
        String refresh_token = null;
        String openid = null;

        if(StringUtils.isNotEmpty(refreshToken)){
            params = getAccessTokenByReshToken(refreshToken);
            access_token = params.get("access_token");
            refresh_token = params.get("refresh_token");
            openid = params.get("openid");
        }
        else if (StringUtils.isNotEmpty(code)){
            params = getAccessTokenByCode(code);
            access_token = params.get("access_token");
            refresh_token = params.get("refresh_token");
            openid = params.get("openid");
        }

        if ("snsapi_userinfo".equals(scope)){
            //拉取用户信息(需scope为 snsapi_userinfo)
            Map<String, String> params1 = new HashedMap();
            params1.put("access_token", access_token);
            params1.put("openid", openid);
            params1.put("lang", "zh_CN");
            String response1 = HttpUtils.sendPost("https://api.weixin.qq.com/sns/userinfo?", params1, null);
            JSONObject jsonObject1 = JSON.parseObject(response1);
            String nickname = jsonObject1.getString("nickname");
            String sex = jsonObject1.getString("sex");
            String province = jsonObject1.getString("province");
            String city = jsonObject1.getString("city");
            String country = jsonObject1.getString("country");
            String headimgurl = jsonObject1.getString("headimgurl");
            String unionid = jsonObject1.getString("unionid");

            map.put("nickname", nickname);
            map.put("sex", sex);
            map.put("province", province);
            map.put("city", city);
            map.put("country", country);
            map.put("headimgurl", headimgurl);
            map.put("unionid", unionid);
        }
        map.put("openid", openid);
        map.put("refresh_token", refresh_token);

        return map;
    }

    /**
     * 通过code获取access_token和openid
     * @param code
     * @return
     * @throws Exception
     */
    private Map<String, String> getAccessTokenByCode(String code) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> params = new HashedMap();
        params.put("appid", gzh_appid);
        params.put("secret", gzh_secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        //通过code换取网页授权access_token
        String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/sns/oauth2/access_token", params, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(response);
        String access_token = jsonObject.getString("access_token");//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        String refresh_token = jsonObject.getString("refresh_token");
        String openid = jsonObject.getString("openid");//用户授权的作用域，使用逗号（,）分隔
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(access_token)){
            throw new ServiceException("2000","获取openid为空");
        }

        map.put("access_token", access_token);
        map.put("refresh_token", refresh_token);
        map.put("openid", openid);
        return map;
    }

    /**
     * 通过refreshToken获取access_toke和openid
     * @param refreshToken
     * @return
     * @throws Exception
     */
    private Map<String, String> getAccessTokenByReshToken(String refreshToken) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> params = new HashedMap();
        params.put("appid", gzh_appid);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);

        //通过code换取网页授权access_token
        String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/sns/oauth2/refresh_token", params, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(response);
        String access_token = jsonObject.getString("access_token");//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        String refresh_token = jsonObject.getString("refresh_token");
        String openid = jsonObject.getString("openid");//用户授权的作用域，使用逗号（,）分隔
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(access_token)){
            throw new ServiceException("2000","获取openid为空");
        }

        map.put("access_token", access_token);
        map.put("refresh_token", refresh_token);
        map.put("openid", openid);
        return map;
    }

    @Override
    public String getGzhAccessToken() throws Exception {
        String access_token = getAccessToken(gzh_appid, gzh_secret);
        return access_token;
    }

    @Override
    public String getAccessToken(String appid, String secret) throws Exception {
        Cache cache = cacheManager.getCache("weixin");
        Cache.ValueWrapper access_token2 = cache.get("access_token");
        Cache.ValueWrapper expires_date2 = cache.get("expires_date");

        String access_token1 = null;
        Date expires_date = null;
        if(access_token2!=null) {
            access_token1 = (String) cache.get("access_token").get();//获取缓存数据
        }
        if(expires_date2!=null) {
            expires_date = (Date) cache.get("expires_date").get();
        }

        if(StringUtils.isEmpty(access_token1) || expires_date==null || expires_date.before(new Date())){
            Map<String, String> params = new HashedMap();
            params.put("appid", appid);
            params.put("secret", secret);
            params.put("grant_type", "client_credential");

            String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/cgi-bin/token", params, null, "utf-8");
            JSONObject jsonObject = JSON.parseObject(response);
            String access_token = jsonObject.getString("access_token");
            Integer expires_in = jsonObject.getInteger("expires_in");//有效时间，秒
            if(StringUtils.isEmpty(access_token)){
                throw new ServiceException("2000","获取access_token为空");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, expires_in-60);
            cache.put("access_token", access_token);
            cache.put("expires_date", calendar.getTime());

            return access_token;
        }else {
            return access_token1;
        }
    }

    @Override
    public Map<String, Object> getJSSdkConfig(String url) throws Exception {
        Cache cache = cacheManager.getCache("weixin");
        Cache.ValueWrapper ticket2 = cache.get("ticket");
        Cache.ValueWrapper expires_date2 = cache.get("expires_date");

        String ticket1 = null;
        Date expires_date = null;
        if(ticket2!=null) {
            ticket1 = (String) cache.get("ticket").get();//获取缓存数据
        }
        if(expires_date2!=null) {
            expires_date = (Date) cache.get("expires_date").get();
        }

        if(StringUtils.isEmpty(ticket1) || expires_date==null || expires_date.before(new Date())){
            Map<String, String> params = new HashedMap();
            params.put("access_token", getGzhAccessToken());
            params.put("type", "jsapi");
            String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params, null, "utf-8");
            JSONObject jsonObject = JSON.parseObject(response);
            String ticket = jsonObject.getString("ticket");
            Integer expires_in = jsonObject.getInteger("expires_in");//有效时间，秒
            if(StringUtils.isEmpty(ticket)){
                throw new ServiceException("2000","获取access_token为空");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, expires_in-60);
            cache.put("ticket", ticket);
            cache.put("expires_date", calendar.getTime());

            ticket1 = ticket;
        }

        String nonceStr = CommonUtils.createRandom(false, 32);
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);

        String string1 = "jsapi_ticket="+ticket1+"&noncestr="+nonceStr+"&timestamp="+timeStamp+"&url="+url;
        String signature = DigestUtils.shaHex(string1);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", gzh_appid);
        map.put("timestamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("signature", signature);

        return map;
    }


}
