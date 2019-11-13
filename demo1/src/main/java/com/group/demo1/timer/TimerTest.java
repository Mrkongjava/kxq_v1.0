package com.group.demo1.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: kxq
 * @Date: 2019/11/13 14:33
 * @Description: 定时器测试类
 */
@Component
public class TimerTest {

    @Scheduled(fixedDelay = 5000)
    public void updateOrder() throws InterruptedException {
        System.out.println("定时任务执行1----------------------" + Thread.currentThread().getName());
        Thread.sleep(2000);
        System.out.println("定时任务1执行完毕");
    }

    @Scheduled(fixedDelay = 1000)
    public void updateOrder2() throws InterruptedException {
        System.out.println("定时任务执行2----------------------" + Thread.currentThread().getName());
        System.out.println("定时任务2执行完毕");
    }
}
