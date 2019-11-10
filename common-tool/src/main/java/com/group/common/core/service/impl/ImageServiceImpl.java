package com.group.common.core.service.impl;

import com.group.common.core.service.ImageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${qiniu.cdn}")
    private String cdn;

    @Override
    public String convertImageUrl(String key) {
        if(StringUtils.isNotEmpty(key)){
            return cdn + key;
        }
        return null;
    }

    @Override
    public String convertImageUrl2(String keys) {
        if (StringUtils.isEmpty(keys)) {
            return null;
        }

        String imagestr = "";
        String[] images = StringUtils.split(keys,",");
        for (int i = 0; i < images.length; i++) {
            if(StringUtils.startsWith(images[i],"http")){
                imagestr += images[i]+";";
            }else {
                imagestr += cdn+images[i]+";";
            }
        }

        return imagestr;
    }

    @Override
    public List<String> convertImageUrl3(String key) {

        String[] slideshowArray = key.split(",");
        List<String> list = new ArrayList<String>();

        for (int i = 0; i <slideshowArray.length ; i++) {
            list.add(cdn + slideshowArray[i]);
        }
        return list;
    }

    @Override
    public String convertImageUrlHtml(String html) {
        String imagestr = "";
        if(!StringUtils.isEmpty(html)){
            String[] ps = StringUtils.substringsBetween(html, "<p>","</p>");
            if(ps!=null && ps.length>0){
                for(int i=0;i<ps.length;i++){
                    String[] images = StringUtils.substringsBetween(ps[i], "src=\"", "\"");
                    if(images!=null && images.length>0){
                        for(int j=0;j<images.length;j++){
                            if(StringUtils.startsWith(images[j],"http")){
                                imagestr += images[j]+";";
                            }else {
                                imagestr += cdn+images[j]+";";
                            }
                        }
                    }
                }
            }
        }
        return imagestr;
    }



}
