package com.group.common.core.service.impl;

import com.aliyun.oss.OSSClient;
import com.group.common.core.service.AliyunOssService;
import com.group.common.utils.OrderUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class AliyunOssServiceImpl implements AliyunOssService {

    @Value("${AccessKeyId}")
    private String AccessKeyId;
    @Value("${AccessKeySecret}")
    private String AccessKeySecret;

    @Override
    public String uploadFile(String bucket, String filePaht, String key) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, AccessKeyId, AccessKeySecret);
        // 上传文件。
        ossClient.putObject(bucket, key, new File(filePaht));
        // 关闭OSSClient。
        ossClient.shutdown();
        return key;
    }

    @Override
    public String uploadByest(String bucket, byte[] bytes, String key) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, AccessKeyId, AccessKeySecret);
        // 上传Byte数组。
        ossClient.putObject(bucket, key, new ByteArrayInputStream(bytes));
        // 关闭OSSClient。
        ossClient.shutdown();
        return key;
    }

    @Override
    public String uploadStream(String bucket, InputStream inputStream, String key) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, AccessKeyId, AccessKeySecret);
        // 数据流。
        ossClient.putObject(bucket, key, inputStream);
        return key;
    }

    @Override
    public String uploadMultipartFile(String bucket, MultipartFile multipartFile, String type) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = StringUtils.substringAfterLast(fileName,".");
        if(StringUtils.isEmpty(suffix)){
            suffix = "jpg";
        }
        String key = type + "/" + OrderUtils.creatOrderNum() + "." + suffix;
        uploadByest(bucket, multipartFile.getBytes(), key);
        return key;
    }
}
