package com.group.common.core.service;

/**
 * 融云service
 */
public interface RongCloudService {

    /**
     * 获取token
     * @param userId
     * @param name
     * @param portraitUri
     * @return
     */
    String getToken(String userId, String name, String portraitUri) throws Exception;

    /**
     * 刷新用户信息
     * @param userId
     * @param name
     * @param portraitUri
     */
    void refresh(String userId, String name, String portraitUri) throws Exception;
}
