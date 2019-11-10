package com.group.common.utils.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加解密工具类
 */
public class AESUtil {

	/**
	 * 加密
	 * @param content  需要加密的内容
	 * @param password 加密密码
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
		    secureRandom.setSeed(password.getBytes());  
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			String result = parseByte2HexStr(cipher.doFinal(byteContent));//加密
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}   catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	public static String decrypt(String content, String password) {
		try {
		    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	        secureRandom.setSeed(password.getBytes());  
			byte[] contentTemp = parseHexStr2Byte(content);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128,secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			String result = new String(cipher.doFinal(contentTemp), "UTF-8");//解密
			return result;  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                    String hex = Integer.toHexString(buf[i] & 0xFF);
                    if (hex.length() == 1) {
                            hex = '0' + hex;
                    }
                    sb.append(hex.toUpperCase());
            }
            return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1){
				return null;
			}

            byte[] result = new byte[hexStr.length()/2];
            for (int i = 0;i< hexStr.length()/2; i++) {
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                    result[i] = (byte) (high * 16 + low);
            }
            return result;
    }
    
     public static void main(String[] args) {
    	 String aaa = "6665FDA4930B353BF8DA7A07BCADC997D26FE4C2EC26A6D4E6CF568815960DC23D5A3512F62A14234081E526FF2ADA78AC90D19F095AED7560AE6AC21EC8FFA3148CB226560B4708C0714372618016A8F070876D0B4361505B4E6BE2123944587D670A892B9C023A16D92095DF3554DD";
    	 System.out.println(decrypt(aaa, "a7cc6c5acf497f8cec13fd9004fcbadf"));
	}

}
