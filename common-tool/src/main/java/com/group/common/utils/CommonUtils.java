package com.group.common.utils;

import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {
	
	/**
	 * 判断一个字符串是否在这个字符串数组中
	 * @param strs 字符串数组
	 * @param s 待查找的字符串
	 * @return true-存在，false-不存在
	 */
	public static boolean isHave(String[] strs, String s) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].indexOf(s) != -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将集合转换成数组
	 * @param list 待转换的集合
	 * @return
	 */
	public static int[] converListtoArray(List<Integer> list){
		if(list==null || list.size()==0) {
			return new int[0];
		}
		
		int[] array = new int[list.size()];
		for(int i=0;i<list.size();i++){
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	  * 创建指定数量的随机字符串
	  * @param numberFlag 是否是数字
	  * @param length
	  * @return
	  */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
	public static Map<String, String> readStringXmlOut(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<Element> iter = rootElt.elementIterator(); //获取跟节点下面所有的子节点

			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				String name = recordEle.getQualifiedName();
				String value = recordEle.getStringValue();
				map.put(name, value);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
     * @description 将xml字符串转换成map
     * @param xml
     * @param key
     * @return Map
     */
	public static String readStringXmlOut(String xml,String key) {
		String value = null;
		try {
			Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<Element> iter = rootElt.elementIterator(key); //获取跟节点下面所有的子节点

			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				value = recordEle.getStringValue();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 转换成xml字符串
	 * @param params
	 * @return
	 */
	public static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");
			sb.append("<![CDATA[").append(params.get(i).getValue()).append("]]>");
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	/**
	 * List集合去重复
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> List<T> qcList(List<T> list) {  
	    List<T> tempList= new ArrayList<T>();  
	    for(T i:list){  
	        if(!tempList.contains(i)){  
	            tempList.add(i);  
	        }  
	    }  
	    return tempList;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * @param strURL url地址
	 * @return url请求参数部分
	 * @author lzf
	 */
	private static String TruncateUrlPage(String strURL){
		String strAllParam=null;
		String[] arrSplit=null;
		strURL=strURL.trim();
		arrSplit=strURL.split("[?]");
		if(strURL.length()>1){
			if(arrSplit.length>1){
				for (int i=1;i<arrSplit.length;i++){
					strAllParam = arrSplit[i];
				}
			}
		}
		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对
	 * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * @param URL  url地址
	 * @return  url请求参数部分
	 * @author lzf
	 */
	public static Map<String, String> urlSplit(String URL){
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit=null;
		String strUrlParam=TruncateUrlPage(URL);
		if(strUrlParam==null){
			return mapRequest;
		}
		arrSplit=strUrlParam.split("[&]");
		for(String strSplit:arrSplit){
			String[] arrSplitEqual=null;
			arrSplitEqual= strSplit.split("[=]");
			//解析出键值
			if(arrSplitEqual.length>1){
				//正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			}else{
				if(arrSplitEqual[0]!=""){
					//只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}



	/**
	 * 创建文件
	 * @param filePath
	 * @param fileName
	 */
	public static File createFile(String filePath, String fileName){
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();//创建目录
		}

		File file1 = new File(filePath, fileName);
		if(!file1.exists()){
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file1;
	}


	public static void main(String[] args) {
		String aa = "<xml><coupon_fee><![CDATA[0.1]]></coupon_fee></xml>";
		Map<String, String> bb = readStringXmlOut(aa);
		System.out.println("===");
	}
}
