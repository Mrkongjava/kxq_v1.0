package com.group.common.utils;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OrderUtils {

	/**
	 * 生成订单号
	 * 使用synchronized避免高并发下生成重复订单号
	 * @return
	 */
	public synchronized static String creatOrderNum(){
		//当前时间 yyyyMMddHHmmss
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currTime = outFormat.format(new Date());
		//四位随机数
		String strRandom = RandomUtil.generateNumber(3);
		String out_trade_no = currTime + strRandom;
		return out_trade_no;
	}
	
	/**
	 * 获取支付通知地址
	 * @param request
	 * @param payWay
	 * @return
	 */
	public static String getNotifyUrl(HttpServletRequest request, String payWay){
		String requestRUL = request.getRequestURL().toString();
		String contextPath = request.getContextPath();
		String notifyUrl = StringUtils.substringBefore(requestRUL, contextPath)+contextPath;
		//notifyUrl = "http://15e7cd44.ngrok.io/quancheng";
    	if("tenpay".equals(payWay)){
    		notifyUrl = notifyUrl+"/channel/weixinpayNotify";
    	}
    	if("alipay".equals(payWay)){
    		notifyUrl = notifyUrl+"/channel/apilypayNotify";
    	}
    	System.out.println("异步通知地址："+notifyUrl);
    	return notifyUrl;
	}

	/**
	 * 统一下单签名

	 签名算法

	 签名生成的通用步骤如下：

	 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。

	 特别注意以下重要规则：
	 ◆ 参数名ASCII码从小到大排序（字典序）；
	 ◆ 如果参数的值为空不参与签名；
	 ◆ 参数名区分大小写；
	 ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
	 ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段

	 第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。

	 key设置路径：微信商户平台(channel.weixin.qq.com)-->账户设置-->API安全-->密钥设置

	 */
	public static String genProductArgs(String out_trade_no,String order_price,String notify_url,String product_name,String ip,String WX_APP_ID,String WX_PARTNER,String WX_API_KEY,String trade_type,String openid) {
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new NameValuePair("appid", WX_APP_ID));
		packageParams.add(new NameValuePair("body", product_name));
		packageParams.add(new NameValuePair("mch_id", WX_PARTNER));
		packageParams.add(new NameValuePair("nonce_str",CommonUtils.createRandom(false, 32)));
		packageParams.add(new NameValuePair("notify_url",notify_url));
		if("JSAPI".equals(trade_type)){
			packageParams.add(new NameValuePair("openid", openid));
		}
		packageParams.add(new NameValuePair("out_trade_no",out_trade_no));
		packageParams.add(new NameValuePair("spbill_create_ip", ip));
		packageParams.add(new NameValuePair("total_fee", order_price));
		packageParams.add(new NameValuePair("trade_type", trade_type));

		String sign = SignUtils.genPackageSign(packageParams, WX_API_KEY);
		packageParams.add(new NameValuePair("sign", sign));

		String xmlstring = CommonUtils.toXml(packageParams);
		return xmlstring;
	}

	/**
	 * 退款签名
	 * @param appid
	 * @param mchid
	 * @param outRefundNo
	 * @param outTradeNo
	 * @param refundFee
	 * @param totalFee
	 * @param apikey
	 * @return
	 */
	public static String getRefundPaySign(String appid, String mchid, String outRefundNo, String outTradeNo, String refundFee, String totalFee, String apikey){
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new NameValuePair("appid", appid));
		packageParams.add(new NameValuePair("mch_id", mchid));
		packageParams.add(new NameValuePair("nonce_str", CommonUtils.createRandom(false, 32)));
		packageParams.add(new NameValuePair("out_refund_no", outRefundNo));
		packageParams.add(new NameValuePair("out_trade_no", outTradeNo));
		packageParams.add(new NameValuePair("refund_fee", refundFee));
		packageParams.add(new NameValuePair("total_fee", totalFee));

		String sign = SignUtils.genPackageSign(packageParams, apikey);
		packageParams.add(new NameValuePair("sign", sign));

		String xmlstring = CommonUtils.toXml(packageParams);
		return xmlstring;
	}

	/**
	 * 调起APP支付签名
	 * 算法同上
	 * @return
	 */
	public static String getAppPaySign(String noncestr,String prepayid,String timeStamp,String appid,String partnerid,String apikey) {
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new NameValuePair("appid", appid));
		packageParams.add(new NameValuePair("noncestr", noncestr));
		packageParams.add(new NameValuePair("package", "Sign=WXPay"));
		packageParams.add(new NameValuePair("partnerid", partnerid));
		packageParams.add(new NameValuePair("prepayid", prepayid));
		packageParams.add(new NameValuePair("timestamp", timeStamp));
		String sign = SignUtils.genPackageSign(packageParams, apikey);
		return sign;
	}

	/**
	 * 调起H5支付签名
	 */
	public static String getH5PaySign(String noncestr,String prepayid,String timeStamp,String appId,String apikey) {
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new NameValuePair("appId", appId));//公众号ID
		packageParams.add(new NameValuePair("nonceStr", noncestr));
		packageParams.add(new NameValuePair("package", "prepay_id="+prepayid));
		packageParams.add(new NameValuePair("signType", "MD5"));
		packageParams.add(new NameValuePair("timeStamp", timeStamp));
		String sign = SignUtils.genPackageSign(packageParams, apikey);
		return sign;
	}


}
