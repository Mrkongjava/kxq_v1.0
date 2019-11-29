//package com.group.common.core.service;
//
//import java.util.Map;
//
///**
// * 微信支付接口（废弃）
// */
//public interface WeixinPayService {
//
//    /**
//     * 统一下单
//     * @param body 商品简单描述
//     * @param outTradeNo 商户系统内部订单号
//     * @param orderPrice 订单总金额，单位为分
//     * @param notifyUrl 异步通知地址
//     * @param spbillCreateIp 终端ip
//     * @param tradeType 交易类型 APP-app JSAPI-公众号 XCXAPI-小程序
//     * @param openid
//     * @return
//     */
//    Map<String, Object> unifiedorder(String body, String outTradeNo, int orderPrice, String notifyUrl, String spbillCreateIp, String tradeType, String openid) throws Exception;
//
//    /**
//     * 退款
//     * @param outTradeNo 商户订单号
//     * @param outRefundNo 商户退款单号
//     * @param totalFee 订单金额
//     * @param refundFee 退款金额
//     * @param tradeType APP-app JSAPI-公众号 XCXAPI-小程序
//     * @param notifyUrl
//     */
//    Map<String, String> refund(String outTradeNo, String outRefundNo, int totalFee, int refundFee, String notifyUrl, String tradeType) throws Exception;
//
//    /**
//     * 验证异步通知参数
//     * @param params
//     * @return
//     */
//    boolean verifyNotify(Map<String, String> params) throws Exception;
//}
