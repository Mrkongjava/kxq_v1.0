package com.group.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * RSA签名
     *
     * @param content       待签名数据
     * @param privateKey    商户私钥
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String input_charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));

            byte[] signed = signature.sign();

            return new BASE64Encoder().encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content        待签名数据
     * @param sign           签名值
     * @param ali_public_key 支付宝公钥
     * @param input_charset  编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String ali_public_key, String input_charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = new BASE64Decoder().decodeBuffer(ali_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));

            boolean bverify = signature.verify(new BASE64Decoder().decodeBuffer(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 解密
     *
     * @param content       密文
     * @param private_key   商户私钥
     * @param input_charset 编码格式
     * @return 解密后的字符串
     */
    public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = new BASE64Decoder().decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static void main(String[] args) {
        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+MmWUth27Q4183mIgGHHgCjgV\n" +
                "YvHaHFegYD38OJsym65A/xF3Isart8y/gxdUZqbMBDZ0hURnqAl/3+oolwCG6Gfm\n" +
                "OvLZctbNCsD0BIKl17jLHxKp4OqMygnT616C5i5pmGv8SsHwohV218inhAwaAl98\n" +
                "8WIkEJrsBhK4jA3llwIDAQAB";

        String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL4yZZS2HbtDjXze\n" +
                "YiAYceAKOBVi8docV6BgPfw4mzKbrkD/EXcixqu3zL+DF1RmpswENnSFRGeoCX/f\n" +
                "6iiXAIboZ+Y68tly1s0KwPQEgqXXuMsfEqng6ozKCdPrXoLmLmmYa/xKwfCiFXbX\n" +
                "yKeEDBoCX3zxYiQQmuwGEriMDeWXAgMBAAECgYARfzdjF+HiUVOZ0id6xmTrdGU2\n" +
                "anTPWN4izdfF9Tspy7KbHhvwNs3KDE2UrK2fwRXFHPoZfA0P8CB3mBTFiSauGZpc\n" +
                "5msH3OIfUjWmn0ChH8MaZ5QHPP5hsJUpjHrpsJBOyFKMznqDtX2ZD0S5v6atBsnL\n" +
                "LG2NHBNcuizZHgemCQJBAPlMz6xfn+w2MjJUglflUDi/xVnzdZGtgbFZ+MAihxmh\n" +
                "YeYmizTSvFIAzahhFqgkFPME9yhcaqg4Fx3qUBqgYcUCQQDDTvRigMjWPkFoU5x6\n" +
                "wRIs9EKA3jQjhPGp3m5FPlquGy0e350d8isu8qA7QqqIPUDpMDuNoWhSf5YgRHZr\n" +
                "BqurAkEAzag/6cM4+nP7FOoCXOAfPbF17K6a3oJz6wp92oKhUNOBH4vMatct47gl\n" +
                "8DGnry2U2qXN/s2+budWqqcRTeXgwQJALbWKyIrvdgS39hyrYKdTA6Ze8h4PAckZ\n" +
                "bfIayKXhPyIpGzKmaN3GcYRjL7tYge4kBO8+4NTHv5cQoZikRkQXZQJAK9TsK689\n" +
                "z6YsNxKU3U3Vaqt7A2YQZcTzIDM/HxFDLi5/rFVkCPTglsf93Ny/sYiEx8OKZbbc\n" +
                "0cqKJGCR7oit6g==";


        String a = sign("fafd", PRIVATE_KEY, "GBK");
        System.out.println("加密后的结果："+a);

        System.out.println("校验签名:"+verify("fafd", a, PUBLIC_KEY, "GBK"));

        try {
            String b = decrypt(a, PRIVATE_KEY, "GBK");
            System.out.println("解密后的结果："+b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}