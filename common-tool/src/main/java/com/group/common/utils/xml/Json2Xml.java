package com.group.common.utils.xml;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * 完美实现json和xml的转换
 */
public class Json2Xml {
    public static void main(String[] args) throws DocumentException {
//        String str = "{\"CargosubmatterList\":{\"Cargosubmatter\":[{\"itemkindcode\":\"005\",\"itemCode\":\"9996\",\"itemname\":\"特约标的\",\"itemddetail\":\"特约标的\",\"rate\":\"10.00\",\"currency\":\"CNY\",\"exchangerate\":\"1.00\",\"shortrateflag\":\"1\",\"shortrate\":\"100.00\",\"calamount\":\"1\",\"mainitem\":\"1\",\"itemkindname\":\"国内公路货物运输定额保险（B款）\",\"itemkindno\":\"1\",\"itemno\":\"1\",\"chgamount\":\"0.00\",\"chgpremium\":\"0.00\"},{\"itemkindcode\":\"006\",\"itemCode\":\"9996\",\"itemname\":\"特约标的\",\"itemddetail\":\"特约标的\",\"rate\":\"0.85\",\"currency\":\"CNY\",\"exchangerate\":\"1.00\",\"shortrateflag\":\"1\",\"shortrate\":\"100.00\",\"calamount\":\"1\",\"mainitem\":\"0\",\"itemkindname\":\"附加第三者责任保险\",\"itemkindno\":\"2\",\"itemno\":\"1\",\"chgamount\":\"0.00\",\"chgpremium\":\"0.00\"}]},\"PlusInfo\":{\"liabilitystartdate\":\"本保单保险责任起期详见保单条款约定\",\"shadowagentcode\":\"M00000003878\",\"shadowagentname\":\"广东广福宏宇保险经纪有限公司福建分公司\",\"shadowagenttype\":\"2102\"}}";
        String str = "{\"MapSet\": {\"MapGroup\": {\"Map\": [{\"SingleTile\": \"ggg\",\"Extension\": { \"ResourceId\": \"aaa\" },\"Type\": \"ccc\"},{\"SingleTile\": \"111\",\"Extension\": { \"ResourceId\": \"222\" },\"Type\": \"333\"}]},\"ddd\": [\"444\",\"123\"]}}";
        System.out.println(str);
        JSONObject json = JSONObject.fromObject(str);
        String result = json2Xml(json,"xml");
//       String str1 = result.replace("<e>","   ");
//        String str2 = str1.replace("</e>","   ");
        System.out.println("json转xml："+result);
        JSONObject xmlJson =  xml2Json(result);
        System.out.println("xml转json："+xmlJson);
    }

    public static String json2Xml(JSONObject json, String rootName) throws DocumentException {
        String sXml = "";
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);
        xmlSerializer.setRootName(rootName);
        String sContent = xmlSerializer.write(json,"utf-8");
        try {
            Document docCon = DocumentHelper.parseText(sContent);
            sXml = docCon.getRootElement().asXML();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return sXml;
    }

    public static JSONObject xml2Json(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
        String  json =  xmlSerializer.read(xml).toString();
        return JSONObject.fromObject(json);
    }

}

