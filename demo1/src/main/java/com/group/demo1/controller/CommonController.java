package com.group.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.image.BufferedImage;

/**
 * @Auther: kxq
 * @Date: 2019/11/10 19:12
 * @Description:
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/getValidateCode")
    public BufferedImage getValidateCode(){
        return null;
    }

}
