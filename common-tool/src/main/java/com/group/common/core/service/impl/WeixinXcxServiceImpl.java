package com.group.common.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.common.core.config.MyWebAppConfig;
import com.group.common.core.enums.FileType;
import com.group.common.core.exception.ServiceException;
import com.group.common.core.service.AliyunOssService;
import com.group.common.core.service.WeixinGzhService;
import com.group.common.core.service.WeixinXcxService;
import com.group.common.utils.HttpClientUtils;
import com.group.common.utils.OrderUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinXcxServiceImpl implements WeixinXcxService {

    @Value("${xcx.appid}")
    private String xcx_appid;
    @Value("${xcx.secret}")
    private String xcx_secret;

    @Autowired
    private WeixinGzhService weixinGzhService;
    @Autowired
    private AliyunOssService aliyunOssService;
    @Autowired
    private WeixinXcxService weixinXcxService;



    @Override
    public String getOpenId(String code) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> params = new HashedMap();
        params.put("appid", xcx_appid);
        params.put("secret", xcx_secret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        //通过code换取网页授权access_token
        String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/sns/jscode2session", params, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(response);
        String openid = jsonObject.getString("openid");//用户授权的作用域，使用逗号（,）分隔
        if(StringUtils.isEmpty(openid)){
            throw new ServiceException("2000","获取openid为空");
        }

        return openid;
    }

    @Override
    public String getXcxAccessToken() throws Exception {
        String access_token = weixinGzhService.getAccessToken(xcx_appid, xcx_secret);
        return access_token;
    }

    @Override
    public String getwxacodeunlimit(String scene, String page) throws Exception {
        Map<String, Object> params = new HashedMap();
        params.put("scene", scene);
        params.put("page", page);
        params.put("width", 430);
        params.put("auto_color", true);
        params.put("is_hyaline", true);


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+getXcxAccessToken());

        StringEntity entity = new StringEntity(JSON.toJSONString(params),"utf-8");
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        //执行请求操作，并拿到结果（同步阻塞）
        String url = null;
        HttpResponse httpResponse = client.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            String key = FileType.图片.getValue()+"-"+ OrderUtils.creatOrderNum()+".jpeg";
            //url = qiniuyunService.uploadByest(EntityUtils.toByteArray(httpEntity), key);
            url = aliyunOssService.uploadByest("desksnack", EntityUtils.toByteArray(httpEntity), key);
        }

        url = MyWebAppConfig.environment.getProperty("qiniu.cdn")+url;
        return StringUtils.replace(url, "http", "https");
    }

    @Override
    public void sendTemplateMsg(String openid, String template_id, String page, String form_id, JSONObject data, String emphasis_keyword) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("touser", openid);
        param.put("template_id", template_id);
        param.put("page", page);
        param.put("form_id", form_id);
        param.put("data", data);
        param.put("emphasis_keyword", emphasis_keyword);

        String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+getXcxAccessToken(), JSON.toJSONString(param), null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(response);
    }

    @Override
    public String getUserActionSetId(String name,String description) throws Exception{

        String token = weixinXcxService.getXcxAccessToken();

        Map<String, String> params = new HashedMap();
        params.put("type", "WEB");
        params.put("name", name);
        params.put("description", description);

        Map<String,String> headers = new HashMap();
        headers.put("Content-Type:","application/json");

        String response = HttpClientUtils.sendPost("https://api.weixin.qq.com/marketing/user_action_sets/add?version=v1.0&access_token="+token ,
                params, headers, "utf-8");
        JSONObject jsonObject = JSON.parseObject(response);
        String code = jsonObject.getString("code");
        String message = jsonObject.getString("message");
        JSONObject data = jsonObject.getJSONObject("data");
        String user_action_set_id = data.getString("user_action_set_id");
        return user_action_set_id;
    }

    @Override
    public void test() throws Exception{

        JSONObject data = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("value", "1");
        data.put("keyword1", jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("value", "1");
        data.put("keyword2", jsonObject2);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("value", "1");
        data.put("keyword3", jsonObject3);

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("value", "1");
        data.put("keyword4", jsonObject4);

        sendTemplateMsg("onFgY483YyDv6cjePWW6ILlgQKJA", "et5aN-9_xUrx1P6cOLuyRxDL0hqkahK9MxzWRebvKhA", null, "c808d9da599499e7593f349c80bfb082", data, null);
    }

}
