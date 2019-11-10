package com.group.common.core.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信小程序接口
 */
public interface WeixinXcxService {

    /**
     * 获取openid（适用于小程序）
     * @param code
     * @return
     * @throws Exception
     */
    String getOpenId(String code) throws Exception;

    /**
     * 获取小程序access_token
     * 说明：access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     * @return
     * @throws Exception
     */
    String getXcxAccessToken() throws Exception;

    /**
     * 获取小程序码
     * 注意：通过该接口生成的小程序码，永久有效，数量暂无限制。用户扫描该码进入小程序后，开发者需在对应页面获取的码中 scene 字段的值，再做处理逻辑。使用如下代码可以获取到二维码中的 scene 字段的值。调试阶段可以使用开发工具的条件编译自定义参数 scene=xxxx 进行模拟，开发工具模拟时的 scene 的参数值需要进行 urlencode
     * @param scene
     * @param page
     * @return
     * @throws Exception
     */
    String getwxacodeunlimit(String scene, String page) throws Exception;

    /**
     * 发送模版消息
     * @param openid
     * @param template_id
     * @param page
     * @param form_id
     * @param data
     * @param emphasis_keyword
     */
    void sendTemplateMsg(String openid, String template_id, String page, String form_id, JSONObject data, String emphasis_keyword) throws Exception;

    /**
     * 小程序广告转化行为数据接入之获取 user_action_set_id，用于标识数据归属权
     * @param name  用户行为源名称
     * @param description   用户行为源描述，字段长度最小 1 字节，长度最大 128 字节
     * @return
     * @throws Exception
     */
    String getUserActionSetId(String name, String description) throws Exception;

    void test() throws Exception;
}
