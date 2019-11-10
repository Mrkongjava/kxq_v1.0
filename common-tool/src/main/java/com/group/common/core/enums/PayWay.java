package com.group.common.core.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 付款方式
 */
public enum PayWay {
    支付宝("alipay"),
    微信支付("tenpay");
    private PayWay(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getEnumName(String value){
        if(StringUtils.isEmpty(value)) {
            return null;
        }
        PayWay[] values = PayWay.values();
        for(int i=0;i<values.length;i++){
            PayWay enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}