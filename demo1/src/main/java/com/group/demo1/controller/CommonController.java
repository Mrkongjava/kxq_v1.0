package com.group.demo1.controller;

import com.group.common.core.annotation.ActionAnnotation;
import com.group.common.core.commons.SysCode;
import com.group.common.core.controller.BaseController;
import com.group.common.core.exception.ServiceException;
import com.group.common.utils.ValidateCode.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: kxq
 * @Date: 2019/11/10 19:12
 * @Description:
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     * @param response
     * @return
     */
    @RequestMapping("/getValidateCode")
    public String getValidateCode(HttpServletResponse response,/**@RequestParam(defaultValue = "123456789")*/ String phone)throws Exception{

//        Assert.hasLength(phone, "phone不能为空");

        ValidateCode validateCode = new ValidateCode();
        validateCode.createCode();
        String code = validateCode.getCode();

        //将验证码放入redis
        stringRedisTemplate.opsForValue().set(phone + "Login",code, 1,TimeUnit.MINUTES);

        try {
            OutputStream out = response.getOutputStream();
            validateCode.write(out);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("2000","验证码生成失败！");
        }
    }

    /**
     * 校验验证码
     * @return
     */
    @RequestMapping("/checkVerifyCode")
    @ActionAnnotation(params={"phone","code"}, encrypt = false)
    public void checkVerifyCode(HttpServletRequest request,HttpServletResponse response){

        @SuppressWarnings("unchecked")
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");

        String phone = params.get("phone");
        String code = params.get("code");
        Assert.hasLength(phone, "phone不能为空");
        Assert.hasLength(code, "code不能为空");

        String value = stringRedisTemplate.opsForValue().get(phone + "Login");
        if (!code.equals(value)){
            throw new ServiceException("2000","验证码有误，请刷新或重新输入");
        }
        renderJson(request,response,SysCode.SUCCESS,"");
    }

}
