package com.group.common.utils;

import com.group.common.utils.encryption.MD5Util;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.*;

/**
 * 签名工具类
 */
public class SignUtils {

    /**
     * 微信签名
     * @param params
     * @param key
     * @return
     */
	public static String genPackageSign(List<NameValuePair> params, String key) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(key);

		String packageSign = MD5Util.MD5(sb.toString());
		return packageSign;
	}

    /**
     * 已废弃
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串
     * @param paraMap   要排序的Map对象
     * @param urlEncode   是否需要URLENCODE
     * @param keyToLower    是否需要将Key转换为全小写 true:key转化成小写，false:不转化
     * @return
     */
    public static String singParam(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower, String appSecert) {
        Map<String, String> tmpMap = paraMap;
        String sign = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                String key = item.getKey();
                String val = item.getValue();
                if (urlEncode) {
                    val = URLEncoder.encode(val, "utf-8");
                }
                if (keyToLower) {
                    buf.append(key.toLowerCase() + "=" + val);
                } else {
                    buf.append(key + "=" + val);
                }
                buf.append("&");
            }
            String buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff += "key="+appSecert;
                System.out.print("签名字符串："+buff+"\n");
                sign = MD5Util.MD5(buff);
                System.out.print("签名结果："+sign+"\n");
            }
        } catch (Exception e) {
            return null;
        }
        return sign;
    }

    //已废弃
    public static String singParam(Map<String, String> paraMap, String appSecert) {
        String sign = singParam(paraMap, false, false, appSecert);
        return sign;
    }

    /**
     * 参数签名（空值的key也要参与签名）
     * @param paramMap
     * @param appSecret
     * @return
     * @throws Exception
     */
    public static String getSign(Map<String, String> paramMap, String appSecret, Logger logger) throws Exception{
        SortedMap<String, String> smap = new TreeMap<String, String>(paramMap);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> m : smap.entrySet()) {
            String value = m.getValue();
            if(value==null) {
                value = "";
            }

            stringBuffer.append(m.getKey()).append("=").append(value).append("&");
        }

        String argPreSign = stringBuffer.append("key=").append(appSecret).toString();
        logger.info("签名字符串：" + argPreSign);
        String signStr = MD5Util.MD5(argPreSign);
        logger.info("签名结果：" + signStr);
        return signStr;
    }

    /**
     * 参数签名（空值的key不参与签名）
     * @param paramMap
     * @param appSecret
     * @param logger
     * @return
     * @throws Exception
     */
    public static String getSign2(Map<String, String> paramMap, String appSecret, Logger logger) throws Exception{
        SortedMap<String, String> smap = new TreeMap<String, String>(paramMap);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> m : smap.entrySet()) {
            String value = m.getValue();
            if(StringUtils.isNotEmpty(value)){
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }

        String argPreSign = stringBuffer.append("key=").append(appSecret).toString();
        logger.info("签名字符串：" + argPreSign);
        String signStr = MD5Util.MD5(argPreSign);
        logger.info("签名结果：" + signStr);
        return signStr;
    }

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        //字典序列排序
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put("total_fee","200");
        paraMap.put("appid", "wxd678efh567hg6787");
        paraMap.put("body", "腾讯充值中心-QQ会员充值");
        paraMap.put("out_trade_no","20150806125346");
        String url = getSign2(paraMap, "111", LoggerFactory.getLogger(SignUtils.class));
        System.out.println(url);
    }
}
