package com.group.demo1;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.group.demo1.mapper")
@ComponentScan(basePackages = {"com.group"})
@EnableTransactionManagement
@EnableAsync
@EnableCaching
@EnableScheduling
public class Demo1Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    //tomcat war包启动
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Demo1Application.class);
    }

}
