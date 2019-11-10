package com.group.common.core.commons;

/**
 * 版权：小月科技 <br/>
 * 作者：yong.chen@rogrand.com <br/>
 * 生成日期：2013年11月6日 <br/>
 * 描述：调用服务接口后返回的操作码 和 信息
 */
public enum SysCode {
    
    SUCCESS("0000", "操作成功"),
    SYS_ERR("1111", "操作错误"),
    PARAM_IS_ERROR("1001", "参数错误"),
    NOT_LOGIN("1002", "未登录"),
    AUTH_CODE_ERR("1004", "验证码错误"),
    ;
    
    private SysCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    private String code;
    private String message;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
