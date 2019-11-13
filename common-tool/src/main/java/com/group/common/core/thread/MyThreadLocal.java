package com.group.common.core.thread;

/**
 * @Auther: kxq
 * @Date: 2019/11/13 12:46
 * @Description: ThreadLocal 是线程本地存储，存储其中的数据线程间不共享，不存在线程安全问题
 **/
public class MyThreadLocal {

    //用户登录后将用户信息存储在Thread中，可随时从Thread中取出
//    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();
//
//    public static void setUser(MiaoshaUser user) {
//        userHolder.set(user);
//    }
//
//    public static MiaoshaUser getUser() {
//        return userHolder.get();
//    }
}
