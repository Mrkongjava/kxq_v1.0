package com.group.demo1.controller;

import com.group.common.core.annotation.ActionAnnotation;
import com.group.demo1.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: kxq
 * @Date: 2019/11/6 12:22
 * @Description:
 */
@Controller
public class TestController {

    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//也可以使用 RedisTemplate

    @RequestMapping("/test")
    @ResponseBody
    @ActionAnnotation(params={"phone"},seconds = 15,maxCount = 5)
    public String test(HttpServletResponse response){

//        Logger logger = LoggerFactory.getLogger(TestController.class);
//
//        //mybatis-plus测试(顺便测试了分页插件)
//        Page<Employee> page = new Page<>(1, 2);
//        List<Employee> employeeList = employeeService.selectPage(page,null).getRecords();
//        System.out.println(JSON.toJSON(employeeList.get(0)));
//
//        //测试redis
//        stringRedisTemplate.opsForValue().set("token","123456");
//        String token = stringRedisTemplate.opsForValue().get("token");
//        logger.info("token:{}",token);

        return "HelloWord";
    }

    public static void main(String[] args) {
        String str = "112";
        int a = str.indexOf(":");
        System.out.println(a);
    }
}
