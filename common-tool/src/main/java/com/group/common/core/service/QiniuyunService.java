package com.group.common.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云
 * Created by dailing on 2017/4/14.
 */
public interface QiniuyunService {

    /**
     * 获取客户端上传凭证
     * @return
     */
    String getToken();

    /**
     * 文件上传：
     * 最简单的就是上传本地文件，直接指定文件的完整路径即可上传。
     * @param filePaht
     */
    String uploadFile(String filePaht, String key);

    /**
     * 字节数组上传：
     * 可以支持将内存中的字节数组上传到空间中。
     * @param bytes
     */
    String uploadByest(byte[] bytes, String key);

    /**
     * 数据流上传：
     * 这里演示的是InputStream对象的上传，适用于所有的InputStream子类。这里的ByteInputStream只用于演示目的，实际用法根据情况而定。
     * @param inputStream
     */
    String uploadStream(InputStream inputStream, String key);

    /**
     * MultipartFile上传
     * @param multipartFile
     * @return
     */
    String uploadMultipartFile(MultipartFile multipartFile, String type) throws IOException;

    /**
     * 转换图片绝对路径
     * @param key
     * @return
     */
    String convertImageUrl(String key);
}
