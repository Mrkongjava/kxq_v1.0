package com.group.demo1.controller;

import com.group.common.core.exception.ServiceException;
import com.group.demo1.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
//    @ResponseBody
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
        throw new ServiceException("2000","统一异常处理");

//        return "HelloWord";
    }
}
