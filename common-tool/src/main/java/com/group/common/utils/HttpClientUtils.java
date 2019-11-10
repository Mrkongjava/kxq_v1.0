package com.group.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    /**
     * 发送post请求（表单方式）
     * @param url 请求url
     * @param map   请求参数组成的map集合
     * @param headers   请求头信息
     * @param encoding  编码格式
     * @return 返回字符串
     * @throws ParseException parse异常
     * @throws IOException IO异常
     */
    public static String sendPost(String url, Map<String,String> map, Map<String,String> headers, String encoding) throws ParseException, IOException {

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //响应结果
        String respContent = "";

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse httpResponse = client.execute(httpPost);

        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            respContent = EntityUtils.toString(httpEntity, encoding);
            EntityUtils.consume(httpEntity);
        }

        //释放链接
        httpResponse.close();

        System.out.println("请求结果："+respContent);
        return respContent;
    }

    /**
     * 发送post请求（json方式）
     * @param url 请求的url
     * @return 返回字符串
     * @throws Exception 异常
     */
    public static String sendPost(String url,String jsonParam) throws Exception {
        return sendPost(url, jsonParam, null, "utf-8");
    }

    /**
     * 发送post请求（json方式）
     * @param url  请求url
     * @param jsonParam json格式的请求参数
     * @param headers 请求头信息
     * @param charset 编码格式
     * @return 返回信息
     * @throws Exception 抛出异常
     */
    public static String sendPost(String url, String jsonParam, Map<String,String> headers, String charset) throws Exception {
        System.out.println("请求地址："+url);
        System.out.println("请求参数："+jsonParam);

        long start = System.currentTimeMillis();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;

        StringEntity entity = new StringEntity(jsonParam,charset);
        entity.setContentEncoding(charset);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        //设置header信息
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse httpResponse = client.execute(httpPost);

        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            respContent = EntityUtils.toString(httpEntity, charset);
            EntityUtils.consume(httpEntity);
        }

        long end = System.currentTimeMillis();

        System.out.println("请求结果："+respContent);
        System.out.println("请求时间："+(end -start)+"ms");
        return respContent;
    }

    /**
     * 发送post请求 (xml方式)
     * @param url   请求url
     * @param jsonParam json格式参数
     * @param headers   请求头信息
     * @param charset   编码格式
     * @return 返回信息
     * @throws Exception    抛出异常
     */
    public static String sendXmlPost(String url, String jsonParam, Map<String,String> headers, String charset) throws Exception {
        System.out.println("请求地址："+url);
        System.out.println("请求参数："+jsonParam);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;

        StringEntity entity = new StringEntity(jsonParam,charset);
        entity.setContentEncoding(charset);
        entity.setContentType("text/xml");
        httpPost.setEntity(entity);

        //设置header信息
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse httpResponse = client.execute(httpPost);

        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            respContent = EntityUtils.toString(httpEntity, charset);
            EntityUtils.consume(httpEntity);
        }

        System.out.println("请求结果："+respContent);
        return respContent;
    }

    /**
     * 发送带SSL证书的post请求（xml方式）
     * @param url   请求url
     * @param jsonParam json请求参数
     * @param headers   请求头信息
     * @param charset   编码格式
     * @param filePath  ssl文件所在路径
     * @return  返回信息
     * @throws Exception    抛出异常
     */
    public static String sendSSLXmlPost(String url, String jsonParam, Map<String,String> headers, String charset, String filePath, String keyPassword) throws Exception {
        System.out.println("请求地址："+url);
        System.out.println("请求参数："+jsonParam);

        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(filePath));
        try {
            keyStore.load(instream, keyPassword.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, keyPassword.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;

        StringEntity entity = new StringEntity(jsonParam,charset);
        entity.setContentEncoding(charset);
        entity.setContentType("text/xml");
        httpPost.setEntity(entity);

        //设置header信息
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse httpResponse = client.execute(httpPost);

        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            respContent = EntityUtils.toString(httpEntity, charset);
            EntityUtils.consume(httpEntity);
        }

        System.out.println("请求结果："+respContent);
        return respContent;
    }

    /**
     * 发送post请求（字节数组方式）
     * @param url   请求url
     * @param bytes 请求参数的字节数组
     * @param headers   请求头信息
     * @param charset   编码格式
     * @return  返回信息
     * @throws Exception    抛出异常
     */
    public static String sendPost(String url, byte[] bytes, Map<String,String> headers, String charset) throws Exception {
        System.out.println("请求地址："+url);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;

        ByteArrayEntity entity = new ByteArrayEntity(bytes);
        entity.setContentEncoding(charset);
        httpPost.setEntity(entity);

        //设置header信息
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse httpResponse = client.execute(httpPost);

        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            respContent = EntityUtils.toString(httpEntity, charset);
            EntityUtils.consume(httpEntity);
        }

        System.out.println("请求结果："+respContent);
        return respContent;
    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param params    请求参数的map集合
     * @param headers   请求头信息
     * @param charset   编码格式
     * @return  返回信息
     * @throws Exception    抛出异常
     */
    public static String sendPostCommon(String url, Map<String, Object> params, Map<String,String> headers, String charset) throws Exception {
        System.out.println("请求地址："+url);
        System.out.println("请求参数："+ JSON.toJSONString(params));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;

        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        if(params!=null){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if(value instanceof String){
                    mEntityBuilder.addTextBody(key, value.toString(), ContentType.APPLICATION_JSON);
                }
                else if(value instanceof File){
                    File file = (File) value;
                    if(file.getName().lastIndexOf("jpg")>0){
                        mEntityBuilder.addBinaryBody(key, file, ContentType.create("image/jpg","UTF-8"), file.getName());
                    }
                    else if(file.getName().lastIndexOf("png")>0){
                        mEntityBuilder.addBinaryBody(key, file, ContentType.create("image/png", "UTF-8"), file.getName());
                    }
                    else {
                        mEntityBuilder.addBinaryBody(key, file);
                    }
                }
                else if (value instanceof MultipartFile){
                    mEntityBuilder.addBinaryBody(key, ((MultipartFile) value).getInputStream(), ContentType.MULTIPART_FORM_DATA, ((MultipartFile) value).getOriginalFilename());
                }
            }
        }

        HttpEntity entity = mEntityBuilder.build();
        httpPost.setEntity(entity);

        //设置header信息
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse httpResponse = client.execute(httpPost);
        Header[] headers1 = httpResponse.getAllHeaders();

        HttpEntity httpEntity = httpResponse.getEntity();
        respContent = EntityUtils.toString(httpEntity, charset);
        EntityUtils.consume(httpEntity);

        System.out.println("请求结果："+respContent);
        return respContent;
    }

    /**
     * 获取HttpServletRequest的json参数
     * @param request   HttpServletRequest对象
     * @return  返回信息
     */
    public static String JsonReq(HttpServletRequest request) {
        BufferedReader br;
        StringBuilder sb = null;
        String reqBody = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            reqBody = reqBody.substring(reqBody.indexOf("{"));
            request.setAttribute("inputParam", reqBody);
            System.out.println("JsonReq reqBody>>>>>" + reqBody);
            return reqBody;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
