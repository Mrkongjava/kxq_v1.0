package com.group.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class ImageUtils {

    /**
     * 获取富文本中的图片
     * @param content
     * @return
     */
    public static List<String> convertImageUrlHtml2(String content){
        List<String> list = new ArrayList<String>();
        if(!StringUtils.isEmpty(content)){
            String[] ps = StringUtils.substringsBetween(content, "<p>","</p>");
            if(ps!=null && ps.length>0){
                for(int i=0;i<ps.length;i++){
                    String[] images = StringUtils.substringsBetween(ps[i], "src=\"", "\"");
                    if(images!=null && images.length>0){
                        for(int j=0;j<images.length;j++){
                            list.add(images[j]);
                        }
                    }
                }
            }
        }
        return list;
    }

}
