package com.group.common.core.service;

import java.util.List;

public interface ImageService {

    /**
     * 转换图片绝对路径
     * @param key
     * @return
     */
    String convertImageUrl(String key);

    /**
     * 转换多张图片绝对路径，返回String字符串
     * @param keys
     * @return
     */
    String convertImageUrl2(String keys);

    /**
     * 转换多张图片绝对路径，返回List集合
     * @param key
     * @return
     */
    List<String> convertImageUrl3(String key);


    /**
     * 获取富文本中的图片内容
     * @param html
     * @return
     */
    String convertImageUrlHtml(String html);

}
