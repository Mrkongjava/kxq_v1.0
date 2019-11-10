package com.group.common.utils.fastjsonUtils;

import com.alibaba.fastjson.serializer.*;
import com.group.common.core.filter.ComplexPropertyPreFilter;

import java.util.Map;

/**
 * 版权：融贯资讯 <br/>
 * 作者：xuan.zhou@rogrand.com <br/>
 * 生成日期：2014-1-10 <br/>
 * 描述：〈Json工具类〉
 */
public class JsonUtils {

    @SuppressWarnings("unused")
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();

        // 使用和json-lib兼容的日期输出格式
        // config.put(java.util.Date.class, new JSONLibDataFormatSerializer());

        // 使用和json-lib兼容的日期输出格式
        // config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());

    }

    private static final SerializerFeature[] features = {
        SerializerFeature.WriteMapNullValue, // 输出空置字段
        SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
        SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
        SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
        SerializerFeature.WriteDateUseDateFormat // 格式化日期
    };

    public static String toJsonString(Object obj) {
        return toJsonString(obj, true);
    }

    public static String toJsonString(Object obj, boolean useDateFormat) {
        return toJsonString(obj, null, null, useDateFormat);
    }

    public static String toJsonString(Object obj, Map<Class<?>, String[]> includes) {
        return toJsonString(obj, includes, null, false);
    }

    public static String toJsonString(Object obj, Map<Class<?>, String[]> includes, boolean useDateFormat) {
        return toJsonString(obj, includes, null, useDateFormat);
    }

    public static String toJsonString(Object obj, Map<Class<?>, String[]> includes, Map<Class<?>, String[]> excludes) {
        return toJsonString(obj, includes, excludes, false);
    }

    public static String toJsonString(Object obj, Map<Class<?>, String[]> includes, Map<Class<?>, String[]> excludes,
            boolean useDateFormat) {
        ComplexPropertyPreFilter filter = new ComplexPropertyPreFilter(includes, excludes);
//        com.group.utils.fastjsonUtils.ImageValuePreFilter filter1 = new com.group.utils.fastjsonUtils.ImageValuePreFilter();
        SerializeFilter[] filters = {filter/*, filter1*/};
        return toJSONString(obj, filters, features, useDateFormat);
    }

    /**
     * 描述：〈重写JSON.toJSONString(Object object, SerializeFilter filter, SerializerFeature[] features)，能控制是否使用日期格式化〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-3-26 <br/>
     */
    private static String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature[] features,
                                       boolean useDateFormat) {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.config(SerializerFeature.WriteDateUseDateFormat, useDateFormat);

            for (SerializeFilter filter : filters){
                if (filter != null) {
                    if (filter instanceof PropertyPreFilter) {
                        serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
                    }

                    if (filter instanceof NameFilter) {
                        serializer.getNameFilters().add((NameFilter) filter);
                    }

                    if (filter instanceof ValueFilter) {
                        serializer.getValueFilters().add((ValueFilter) filter);
                    }

                    if (filter instanceof PropertyFilter) {
                        serializer.getPropertyFilters().add((PropertyFilter) filter);
                    }
                }
            }

            serializer.write(object);

            String result = out.toString();
            return result;
        } finally {
            out.close();
        }
    }
}
