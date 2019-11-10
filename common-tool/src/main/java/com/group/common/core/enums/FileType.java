package com.group.common.core.enums;

import org.apache.commons.lang.StringUtils;

public enum FileType {
    图像("HEAD_IMAGE"), 图片("PICTURE"), 视频("VIDEO");

    private FileType(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getEnumName(String value){
        if(StringUtils.isEmpty(value)) {
            return null;
        }
        FileType[] values = FileType.values();
        for(int i=0;i<values.length;i++){
            FileType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
