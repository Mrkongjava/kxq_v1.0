package com.group.common.utils.base64;

import org.apache.commons.net.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * base64工具类
 * Created by liufeidong on 2017/11/17.
 */
public class Base64Util {
    //加密
    public static String base64Encoder(String str) {
        BASE64Encoder encode = new BASE64Encoder();
        String result = null;
        try {
            result = encode.encode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    //解密
    public static String base64Decoder(String str) {
        BASE64Decoder decode = new BASE64Decoder();
        String result = "";
        byte[] b;
        try {
            b = decode.decodeBuffer(str);
            result = new String(b, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将文件转base64字符串
     *
     * @param path
     * @return String
     * @date 2018年3月20日
     * @author changyl
     * File转成编码成BASE64
     */
    public static  String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * base64转文件
     * @param destPath
     * @param base64
     * @param fileName
     */
    public static void base64ToFile(String destPath,String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64);
            file=new File(filePath+"/"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
