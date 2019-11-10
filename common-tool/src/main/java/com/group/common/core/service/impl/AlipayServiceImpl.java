package com.group.common.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppQueryRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.group.common.core.exception.ServiceException;
import com.group.common.core.service.AlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {

    Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    //应用ID
    @Value("${APP_ID}")
    private String APP_ID;
    //商户应用私钥
    @Value("${APP_PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;
    //应用公钥
    @Value("${APP_PUBLIC_KEY}")
    private String APP_PUBLIC_KEY;
    //支付宝公钥
    @Value("${ALIPAY_PUBLIC_KEY}")
    private String ALIPAY_PUBLIC_KEY;

    private String CHARSET = "utf-8";

    @Override
    public String createAlipayOrder(String body, String subject, String outTradeNo, String totalAmount, String notifyUrl) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, APP_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.channel
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            //调用支付宝sdk中的sdkExecute方法
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String result = response.getBody();
            logger.info("支付宝生成预支付订单：订单号{},订单金额{},支付宝支付响应数据：{}",outTradeNo,totalAmount,result);//就是orderString 可以直接给客户端请求，无需再做处理。
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void transfer(String out_biz_no, String payee_type, String payee_account, String amount, String payer_show_name, String payee_real_name, String remark) throws Exception{
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APP_ID,APP_PRIVATE_KEY,"json",CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();

        Map<String, Object> bizContent = new HashMap();
        bizContent.put("out_biz_no", out_biz_no);
        bizContent.put("payee_type", payee_type);
        bizContent.put("payee_account", payee_account);
        bizContent.put("amount", amount);
        bizContent.put("payer_show_name", payer_show_name);
        bizContent.put("payee_real_name", payee_real_name);
        bizContent.put("remark", remark);

        request.setBizContent(JSON.toJSONString(bizContent));
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("支付宝转账：订单号{}，转账到支付宝账户【{}】【{}】成功",out_biz_no,payee_account,amount);
        } else {
            logger.info("支付宝转账：订单号{}，转账到支付宝账户【{}】【{}】失败",out_biz_no,payee_account,amount);
            throw new ServiceException("2000", "转账到支付宝账户失败");
        }
    }

    @Override
    public boolean verifyNotify(Map requestParams) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET,"RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public AlipayOpenAuthTokenAppResponse AuthTokenApp() throws AlipayApiException {
        //支付吧SDK提供的的签名验签方法，商户只需要配置相应参数不需要再进行签名验签操作
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APP_ID,APP_PRIVATE_KEY,"json",CHARSET,ALIPAY_PUBLIC_KEY);
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        //必传的请求参数
        request.setBizContent(""
                + "{\"grant_type\":\"authorization_code\","
                + "\"code\":\"P6c8b53fcdd7345d7a8179b57acb0d30\","
                +"}"
                + "");
        AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
        //同步返回的结果
        System.out.println(response.getBody());
        return response;
    }

    @Override
    public AlipayOpenAuthTokenAppQueryResponse AuthTokenAppQuery() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APP_ID,APP_PRIVATE_KEY,"json",CHARSET,ALIPAY_PUBLIC_KEY);
        AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest();
        request.setBizContent("{\"app_auth_token\":\"201810BBe736ec39f1414c98819cef11c685aX30\"}");
        AlipayOpenAuthTokenAppQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        return response;
    }

}
