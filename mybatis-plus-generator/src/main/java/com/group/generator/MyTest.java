package com.group.generator;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: kxq
 * @Date: 2019/11/8 17:44
 * @Description:
 */
public class MyTest {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1);
        jsonObject.put("name","");

        System.out.println(jsonObject.toJSONString());
    }
}
