//package com.group.common.core.service.impl;
//
//import com.group.common.core.exception.ServiceException;
//import com.group.common.core.service.WeixinPayService;
//import com.group.common.utils.CommonUtils;
//import com.group.common.utils.HttpClientUtils;
//import com.group.common.utils.OrderUtils;
//import com.group.common.utils.SignUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//（废弃）
//@Service
//public class WeixinPayServiceImpl implements WeixinPayService {
//
//    private Logger logger = LoggerFactory.getLogger(WeixinPayServiceImpl.class);
//
//    //APP
//    @Value("${app.appid}")
//    private String app_appid;
//    @Value("${app.mchid}")
//    private String app_mchid;
//    @Value("${app.apiKey}")
//    private String app_apiKey;
//
//    //公众号
//    @Value("${gzh.appid}")
//    private String gzh_appid;
//    @Value("${gzh.mchid}")
//    private String gzh_mchid;
//    @Value("${gzh.apiKey}")
//    private String gzh_apiKey;
//
//    //小程序
//    @Value("${xcx.appid}")
//    private String xcx_appid;
//    @Value("${xcx.mchid}")
//    private String xcx_mchid;
//    @Value("${xcx.apiKey}")
//    private String xcx_apiKey;
//
//
//    @Override
//    public Map<String, Object> unifiedorder(String body, String outTradeNo, int orderPrice, String notifyUrl, String spbillCreateIp, String tradeType, String openid) throws Exception{
//
//        String param = "";
//        if("APP".equals(tradeType)){
//            param = OrderUtils.genProductArgs(outTradeNo,orderPrice+"",notifyUrl,body,spbillCreateIp,app_appid,app_mchid,app_apiKey,tradeType,openid);
//        }
//        if("JSAPI".equals(tradeType)){
//            param = OrderUtils.genProductArgs(outTradeNo,orderPrice+"",notifyUrl,body,spbillCreateIp,gzh_appid,gzh_mchid,gzh_apiKey,tradeType,openid);
//        }
//        if("XCXAPI".equals(tradeType)){
//            param = OrderUtils.genProductArgs(outTradeNo,orderPrice+"",notifyUrl,body,spbillCreateIp,xcx_appid,xcx_mchid,xcx_apiKey,"JSAPI",openid);
//        }
//
//        String result = HttpClientUtils.sendXmlPost("https://api.mch.weixin.qq.com/pay/unifiedorder", param, null, "utf-8");
//
//        logger.info("微信支付生成预订单返回结果："+result);
//        Map<String, String> map = CommonUtils.readStringXmlOut(result);
//        String return_code = map.get("return_code");
//        if(!"SUCCESS".equals(return_code)){
//            String return_msg = map.get("return_msg");
//            throw new ServiceException("2000",return_msg);
//        }
//
//        String appId = map.get("appid");
//        String nonceStr = CommonUtils.createRandom(false, 32);
//        String prepay_id = map.get("prepay_id");//微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
//        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
//
//        //调起支付签名
//        Map<String, Object> resultmap = new HashMap<String, Object>();
//        if("APP".equals(tradeType)){
//            String paySign = OrderUtils.getAppPaySign(nonceStr,prepay_id,timeStamp,app_appid,app_mchid,app_apiKey);
//            resultmap.put("appid", appId);
//            resultmap.put("partnerid", app_mchid);
//            resultmap.put("prepayid", prepay_id);
//            resultmap.put("package", "Sign=WXPay");
//            resultmap.put("noncestr", nonceStr);
//            resultmap.put("timestamp", timeStamp);
//            resultmap.put("sign", paySign);
//        }
//        if("JSAPI".equals(tradeType)){
//            String paySign = OrderUtils.getH5PaySign(nonceStr,prepay_id,timeStamp,gzh_appid,gzh_apiKey);
//            resultmap.put("appId", appId);//公众号ID
//            resultmap.put("timeStamp", timeStamp);
//            resultmap.put("nonceStr", nonceStr);
//            resultmap.put("package", "prepay_id="+prepay_id);
//            resultmap.put("signType", "MD5");
//            resultmap.put("sign", paySign);
//        }
//        if("XCXAPI".equals(tradeType)){
//            String paySign = OrderUtils.getH5PaySign(nonceStr,prepay_id,timeStamp,xcx_appid,xcx_apiKey);
//            resultmap.put("appId", appId);//公众号ID
//            resultmap.put("timeStamp", timeStamp);
//            resultmap.put("nonceStr", nonceStr);
//            resultmap.put("package", "prepay_id="+prepay_id);
//            resultmap.put("signType", "MD5");
//            resultmap.put("sign", paySign);
//        }
//
//        return resultmap;
//    }
//
//    @Override
//    public Map<String, String> refund(String outTradeNo, String outRefundNo, int totalFee, int refundFee, String notifyUrl, String tradeType) throws Exception{
//
//        String result = null;
//        if("JSAPI".equals(tradeType)){
//            result = refund(outTradeNo, outRefundNo, totalFee, refundFee, notifyUrl, gzh_appid, gzh_mchid, gzh_apiKey);
//        }
//        if("XCXAPI".equals(tradeType)){
//            result = refund(outTradeNo, outRefundNo, totalFee, refundFee, notifyUrl, xcx_appid, xcx_mchid, xcx_apiKey);
//        }
//
//        Map<String, String> map = CommonUtils.readStringXmlOut(result);
//        String return_code = map.get("return_code");
//        if(!"SUCCESS".equals(return_code)){
//            String return_msg = map.get("return_msg");
//            throw new ServiceException("2000",return_msg);
//        }
//
//        return map;
//    }
//
//    public String refund(String outTradeNo, String outRefundNo, int totalFee, int refundFee, String notifyUrl, String appid, String mchid, String apiKey) throws Exception{
//        String calssPath =Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        String certPath = calssPath+"cert/apiclient_cert.p12";
//        String keyPassword = mchid;
//        String param = OrderUtils.getRefundPaySign(appid, mchid, outRefundNo, outTradeNo, refundFee+"", totalFee+"", apiKey);
//    String result = HttpClientUtils.sendSSLXmlPost("https://api.mch.weixin.qq.com/secapi/pay/refund", param, null, "utf-8", certPath, keyPassword);
//        logger.info("微信退款返回结果："+result);
//        return result;
//}
//
//    @Override
//    public boolean verifyNotify(Map<String, String> params) throws Exception{
//        String trade_type = (String) params.get("trade_type");
//        String sign = (String) params.get("sign");
//        params.remove("sign");
//
//        String sign1 = null;
//        if("JSAPI".equals(trade_type)){
//            sign1 = SignUtils.getSign2(params, gzh_apiKey, logger);
//        }
//        if("APP".equals(trade_type)){
//            sign1 = SignUtils.getSign2(params, app_apiKey, logger);
//        }
//
//        if(sign.equals(sign1)){
//            logger.info("===微信支付结果通知校验成功===");
//            return true;
//        }else {
//            logger.info("===微信支付结果通知校验失败===");
//            return false;
//        }
//    }
//}
