package com.group.common.utils.base64;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Auther: kxq
 * @Date: 2019/9/3 15:34
 * @Description: 将图片转base64、将base64字符串转图片
 *
 * 第一种是用sun公司的sun.misc.BASE64Encoder; jar包进行转码，会有一些特殊字符比如\r \n 之类的，
 * 这些字符在写到url中的时候，会造成url换行之类的效果，从而导致请求出错，第二种方式是用
 * com.sun.org.apache.xerces.internal.impl.dv.util.Base64; jar包来进行转码，这个会把特殊字符转换成 _ - 之类的
 * 合法字符，比较nice
 */
public class ImageToBase64 {

    /**
     * 根据图片地址转换为base64编码字符串
     * @param imgURL 图片路径
     * @return .
     */
    public static String imgBase64(String imgURL) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "fail";//连接失败/链接失效/图片不存在
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while ((len = inStream.read(data)) != -1) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outPut.toByteArray());
    }

    /**
     * 将本地图片转换成字符串 2
     * @param filePath 文件的路径
     * @return String
     */
    public static String encode(String filePath) {
        BufferedInputStream inputStream = null;
        byte[] b = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            b = new byte[inputStream.available()];
            inputStream.read(b);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Base64.encode(b);
    }


    /**
     * base64字符串转化成图片
     *
     * @param imgStr    base64字符串
     * @param imageName 本地路径
     */
    public static boolean GenerateImage(String imgStr, String imageName) {
        if (imgStr == null) //图像数据为空
        {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = imageName;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {

        String str = imgBase64("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3778441103,4276325693&fm=85&app=63&f=JPEG?w=121&h=75&s=30967BDA4AF001A56A89291C0300D064");
//        String str = encode("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3778441103,4276325693&fm=85&app=63&f=JPEG?w=121&h=75&s=30967BDA4AF001A56A89291C0300D064");
        System.out.println(str);
    }
}


