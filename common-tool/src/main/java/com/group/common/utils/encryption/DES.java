package com.group.common.utils.encryption;

import org.apache.commons.net.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 DES加密介绍
 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现
 。
 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DES {

    public DES() {
    }

    //测试
    public static void main(String args[]) {
        //待加密内容
        String str = "ape!@#";
        //密码，长度要是8的倍数
        String password = "Ug2o1Q9d";

        System.out.println("原始数据：" + str);

        //直接将如上内容解密
        try {
            String result = DES.encrypt(str ,password);
            System.out.println("加密后："+ result);

            String decryResult = DES.decrypt(result, password);
            System.out.println("解密后：" + decryResult );
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 加密
     * @param datasource byte[]
     * @param password String
     * @return byte[]
     */
    public static String encrypt(String datasource, String password) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            byte[] str = cipher.doFinal(datasource.getBytes());

            //进行base64编码
            return Base64.encodeBase64String(str);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param ciphertext 密文
     * @param password  密码
     * @return
     * @throws Exception
     */
    public static String decrypt(String ciphertext, String password) throws Exception {

        try {
            //进行base64解密
            byte[] bsseSrc = Base64.decodeBase64(ciphertext);

            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            byte[] bytes = cipher.doFinal(bsseSrc);

            return new String(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    public static byte[] encrypt(byte[] data, byte[] key) {
//        try {
//            // DES算法要求有一个可信任的随机数源
//            SecureRandom sr = new SecureRandom();
//            // 从原始密钥数据树立DESKeySpec对象
//            DESKeySpec dks = new DESKeySpec(key);
//            // 树立一个密匙工厂，然后用它把DESKeySpec转换成
//            // 一个SecretKey对象
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//            SecretKey secretKey = keyFactory.generateSecret(dks);
//            // using DES in ECB mode
//            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
//            // 用密匙原始化Cipher对象
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
//            // 执行加密操作
//            byte encryptedData[] = cipher.doFinal(data);
//            return encryptedData;
//        } catch (Exception e) {
//            System.err.println("DES算法，加密数据出错!");
//            e.printStackTrace();
//        }
//        return null;
//    }
}