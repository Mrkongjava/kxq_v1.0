package com.group.common.utils.encryption;


import java.util.Random;

public class EncryptionUtil {
	private static String key = "a7cc6c5acf497f8cec13fd9004fcbadf"; // 默认密钥
	private static int RANDOM_LENGTH = 16; // 16位随机数
	public final static String DATA_ERROR = "Data error!";
	
	private EncryptionUtil() { }
	
	private static EncryptionUtil encryptionUtil = null;
	
	public static synchronized EncryptionUtil getEncryptionUtil() {
		if (encryptionUtil == null) {
			encryptionUtil = new EncryptionUtil();
		}
		
		return encryptionUtil;
	}

	/**
	 * 对接口的数据进行加密
	 * @param content 明文
	 * @return String 密文
	 */
	public static String encodeContent(String content) {
		return encodeContent(content, key);
	}
	
	/**
	 * 对接口的数据进行加密
	 * @param content 明文
	 * @param key 自定义key
	 * @return String 密文
	 */
	public static String encodeContent(String content, String key) {
		// 获取16位随机数
		String randomStr = getRandom(RANDOM_LENGTH);
		String contentStr = randomStr + content;
		// MD5
		String md5Str = MD5Util.MD5(contentStr);
		// 加密
		return AESUtil.encrypt((contentStr + md5Str), key);
	}

	/**
	 * 对接口的数据进行解密
	 * @param content 密文
	 * @return String 明文
	 */
	public static String decodeContent(String content) {
		return decodeContent(content, key);
	}

	/**
	 * 对接口的数据进行解密
	 * @param content 密文
	 * @param key 自定义key
	 * @return String 明文
	 */
	public static String decodeContent(String content, String key) {
		String result = DATA_ERROR;
		content = AESUtil.decrypt(content, key);
		// 解密
		String contentStr = content.substring(0, content.length() - 32);
		String md5Str = content.substring(content.length() - 32, content.length());
		String checkMd5Str = MD5Util.MD5(contentStr);
		if (md5Str.equals(checkMd5Str)) {
			result = contentStr.substring(16, contentStr.length());
		}
		return result;
	}

	/**
	 * 生成长度小于或等于16位的随机数
	 * @param length 随机数长度
	 * @return String 随机数
	 */
	public static String getRandom(int length) {// length 小于或等于16
	    if (length > 16){
            return  null;
        }
        String substring ;
        String random = new Random().nextLong() + "";
        if(random.length() > 16){
            substring = random.substring(0, length);
        }else{
            substring = getRandom(length);
        }
        return substring;
	}
	
	public static void main(String[] args) {

        System.out.println(getRandom(17));
//		System.out.print(EncryptionUtil.encodeContent("[{\"custOrderId\":\"O201803231118580003\",\"pickupFailReason\":\"省行政区划代码为空或超长\",\"pickupStatus\":\"4\",\"expressNbr\":\"\"}]", "a7cc6c5acf497f8cec13fd9004fcbadf"));
//		System.out.print(EncryptionUtil.decodeContent(EncryptionUtil.encodeContent("我的好")));
//		String decodeContent = EncryptionUtil.decodeContent("11EE7AE1E89685F1F6F64ED59381E3B4104D73781D8BA097BF997F925396F4BB8A5008CDED16D2A1505820D772C84C36561549838A090CA30C3D15A6EAFB5F6C9941676AB4A5309E87F230125D236865");
//		System.out.println(decodeContent);

	}
}
