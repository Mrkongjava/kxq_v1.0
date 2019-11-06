package com.group.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: kxq
 * @Date: 2019/11/6 12:22
 * @Description:
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){


        return "helloWord";
    }
}
