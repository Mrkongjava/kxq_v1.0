package com.group.common.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    /**
     * 读取BufferedReader中的内容
     * @param br
     * @return
     * @throws IOException
     */
    public static String read(BufferedReader br) throws IOException{
        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        return wholeStr;
    }
}
