package com.group.common.core.filter;//package com.group.core.filter;
//
//import com.alibaba.fastjson.serializer.ValueFilter;
//import com.group.core.config.MyWebAppConfig;
//import org.apache.commons.lang.StringUtils;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 使用该过滤器的规则：
// * 1、多图在同一个字段中，分隔符只能使用英文分号，即 ;
// * 2、上传图片的时候，返回图片的相对路径的时候，使用map集合返回，且key带上下划线开头，如 _imageUrl ，这样就不会被加上cdm
// */
//public class ImageValuePreFilter implements ValueFilter{
//
//    private String[] suffixs = {"jpg","jpeg","png","doc","docx","zip","rar","ppt","pptx","xlsx","xls","mp4","avi","mkv","wmv","flv","rmvb","gif"};
//
//    @Override
//    public Object process(Object o, String propertyName, Object propertyValue) {
//
//        if(propertyValue!=null && propertyValue instanceof String){
//            /**
//             * 若key带有前缀下划线，就直接返回value，而不加host（因为上传文件的时候，返回的文件名也是带后缀的，但是这些文件需要存到数据库，而不想带后缀；
//             * 此时就将key带上前缀下划线，这里就不加后缀）
//             */
//
//            if (StringUtils.startsWith(propertyName, "_")) {
//                return propertyValue;
//            }
//
//            //值不为空，且值为String的实例
//            //若以http结尾，那么就表示已经转为绝对路径了，直接过滤掉就好，不用再次转绝对路径
//            if(StringUtils.startsWith(propertyValue.toString(), "http")){
//                //若值以http开头，即已有cdn
//                return propertyValue;
//            }
//
//            //富文本都是以<开头的，因此服务器都将其过滤掉就好，不要用再次转绝对路径
//            if(StringUtils.startsWith(propertyValue.toString(), "<")){
//                //若值以http开头，即已有cdn
//                return propertyValue;
//            }
//
//            String[] str =((String) propertyValue).split(";");
//            if (str.length==1){
//                //获得后缀
//                String suffix = StringUtils.substringAfterLast(propertyValue.toString(), ".");
//                if(StringUtils.isNotEmpty(suffix)){
//                    //将此 String 中的所有字符都转换为小写
//                    suffix = suffix.toLowerCase();
//                    List<String> list = Arrays.asList(suffixs);
//                    if(list.contains(suffix)){
//                        String cdn = MyWebAppConfig.environment.getProperty("ali.oos.baseurl");
//                        return cdn + "/" + propertyValue;
//                    }
//                }
//            }else{
//                String strs = "";
//                for (int i = 0; i < str.length; i++) {
//                    String suffix = StringUtils.substringAfterLast(str[i], ".");
//                    if(StringUtils.isNotEmpty(suffix)){
//                        //将此 String 中的所有字符都转换为小写
//                        suffix = suffix.toLowerCase();
//                        List<String> list = Arrays.asList(suffixs);
//                        if(list.contains(suffix)){
//                            String cdn = MyWebAppConfig.environment.getProperty("ali.oos.baseurl");
//
//                            if (str.length-1==i){
//                                strs = strs + cdn + "/" + str[i];
//                            }else{
//                                strs = strs + cdn + "/" + str[i]+";";
//                            }
//
//                        }
//                    }
//                }
//                return strs;
//            }
//        }
//        return propertyValue;
//    }
//}
