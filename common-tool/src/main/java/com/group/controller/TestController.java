package com.group.controller;

import com.group.common.utils.ValidateCode.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;

/**
 * @Auther: kxq
 * @Date: 2019/11/9 18:34
 * @Description:
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("index")
    public BufferedImage test(){
        ValidateCode validateCode = new ValidateCode();
        validateCode.createCode();
        BufferedImage bufferedImage = validateCode.getBuffImg();
        String code = validateCode.getCode();

        System.out.println(code);
        return bufferedImage;
    }
}
