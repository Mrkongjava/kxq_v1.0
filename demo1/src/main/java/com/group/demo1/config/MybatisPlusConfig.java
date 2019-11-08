package com.group.demo1.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: kxq
 * @Date: 2019/11/8 10:44
 * @Description:
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页 插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁 插件；使用该插件还需要在实体类对应进行乐观锁操作的字段加上注解@Version
     * @return
     */
//     @Bean
//     public OptimisticLockerInterceptor optimisticLockerInterceptor() { return new OptimisticLockerInterceptor(); }

    /**
     * 逻辑删除 插件;还需要在实体类上使用注解@TableLogic 标记逻辑删除的字段,并在配置文件中设置删除和非删除状态
     * @return
     */
//    @Bean
//     public ISqlInjector sqlInjector() { return new LogicSqlInjector();
//    }
}
