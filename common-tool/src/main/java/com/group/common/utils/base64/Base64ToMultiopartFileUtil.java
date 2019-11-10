package com.group.common.utils.base64;

//import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @Auther: kxq
 * @Date: 2019/7/10 15:38
 * @Description: 将base64加密的图片或者pdf转为MultipartFile
 *
 * 注意事项：base64字符串在http传输过程中会将该base64字符串中的所有 “+”字符会转为空格，因此调用对当前方法
 * 将base64转换为图片前，需要将base64中的空格都替换为“+”字符，否则会转换失败
 */
public class Base64ToMultiopartFileUtil {

    static Logger log = LoggerFactory.getLogger(Base64ToMultiopartFileUtil.class);
    //base64转图片
    public static MultipartFile base64ImageToMultipart(String base64) {
        try {
            String[] baseStr = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStr[1]);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStr[0]);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    //base64转pdf
    public static MultipartFile base64PdfToMultipart(String base64) {
        byte[] bytes = Base64.decodeBase64(base64);
        return new BASE64DecodedMultipartFile(bytes, "data:pdf/pdf;base64,");
    }

}

