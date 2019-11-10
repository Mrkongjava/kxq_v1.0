package com.group.demo1.controller;

import com.group.common.core.exception.ServiceException;
import com.group.common.utils.ValidateCode.ValidateCode;
import com.group.common.utils.ValidateCode.ValidateCode1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Auther: kxq
 * @Date: 2019/11/10 19:12
 * @Description:
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    /**
     * 生成验证码
     * @param response
     * @return
     */
    @RequestMapping("/getValidateCode")
    public String getValidateCode(HttpServletResponse response){

        ValidateCode1 validateCode1 = new ValidateCode1();
        validateCode1.createVerifyCode();
        Integer code = validateCode1.getCode();
        BufferedImage buffImg = validateCode1.getBuffImg();
        System.out.println(code);

        //将验证码放于redis中

        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(buffImg, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("2000","验证码生成失败！");
        }

//        try {
//            OutputStream out = response.getOutputStream();
//            ValidateCode validateCode = new ValidateCode();
//            validateCode.createCode();
//            String code = validateCode.getCode();
//            validateCode.write(out);
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ServiceException("2000","验证码生成失败！");
//        }
    }

}
