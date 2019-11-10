package com.group.common.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里云OSS
 */
public interface AliyunOssService {

    /**
     * 本地文件上传
     * @param bucket
     * @param filePaht
     * @param key
     * @return
     */
    String uploadFile(String bucket, String filePaht, String key);

    /**
     * 字节数组上传
     * @param bucket
     * @param bytes
     * @param key
     * @return
     */
    String uploadByest(String bucket, byte[] bytes, String key);

    /**
     * 数据流上传
     * @param bucket
     * @param inputStream
     * @param key
     * @return
     */
    String uploadStream(String bucket, InputStream inputStream, String key);

    /**
     * MultipartFile上传
     * @param bucket
     * @param multipartFile
     * @param type
     * @return
     * @throws IOException
     */
    String uploadMultipartFile(String bucket, MultipartFile multipartFile, String type) throws IOException;
}
