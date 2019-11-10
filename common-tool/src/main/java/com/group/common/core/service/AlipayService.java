package com.group.common.core.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;

import java.util.Map;

/**
 * 支付宝支付
 */
public interface AlipayService {


    /**
     * 创建支付宝订单
     *
     * @param body        商品描述
     * @param subject     商品名称
     * @param outTradeNo  订单号
     * @param totalAmount 订单总金额，单位为元，精确到小数点后两位
     * @param notifyUrl 回调地址（可以为ip、可以为域名）
     * @return
     */
    String createAlipayOrder(String body, String subject, String outTradeNo, String totalAmount, String notifyUrl);

    /**
     * 单笔转账到支付宝账户接口
     * @param out_biz_no 商户转账唯一订单号
     * @param payee_type 收款方账户类型。可取值： 1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。  2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
     * @param payee_account 收款方账户
     * @param amount 转账金额，单位：元。
     * @param payer_show_name 付款方姓名（可选）
     * @param payee_real_name 收款方真实姓名（可选）
     * @param remark 转账备注（可选）
     */
    void transfer(String out_biz_no, String payee_type, String payee_account, String amount, String payer_show_name, String payee_real_name, String remark) throws Exception;

    /**
     * 验证异步通知信息参数
     *
     * @param params
     * @return
     */
    boolean verifyNotify(Map params);

    /**
     * alipay.open.auth.token.app (换取应用授权令牌)
     * @return
     * @throws AlipayApiException
     */
    AlipayOpenAuthTokenAppResponse AuthTokenApp() throws AlipayApiException;

    /**
     * alipay.open.auth.token.app.query (查询某个应用授权AppAuthToken的授权信息)
     * @return
     * @throws AlipayApiException
     */
    AlipayOpenAuthTokenAppQueryResponse AuthTokenAppQuery() throws AlipayApiException;

}
