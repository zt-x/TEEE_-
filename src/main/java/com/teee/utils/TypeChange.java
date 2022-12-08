package com.teee.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class TypeChange {
    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片地址
     * @return
     */
    public static String getImgBase(String imgFile) {

        // 将图片文件转化为二进制流
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 图片头
        //String imghead = "data:image/jpeg;base64,";
        return Base64.encodeBase64String(data);
    }

    public static ArrayList<String> str2arrl(String str, String sep){
        if ("".equals(str) || "[]".equals(str) || str == null){
            return new ArrayList<>();
        }else{
            return new ArrayList<>(Arrays.asList(
                    str.substring(1, str.length() - 1).split(sep)
            ));
        }
    }
    public static ArrayList<String> str2arrl(String str){
        if ("".equals(str) || "[]".equals(str) || str == null){
            return new ArrayList<>();
        }else{
            return new ArrayList<>(Arrays.asList(
                    str.substring(1, str.length() - 1).split(", ")
            ));
        }
    }
    public static JSONArray str2Jarr(String str){
        return JSONArray.parseArray(str);
    }

    public static String arr2str(JSONArray jarr){
        String str = "";
        ArrayList<String> as = new ArrayList<>(jarr);
        return as.toString();
    }
    public static String arrL2str(ArrayList arr){
        return arr.toString();
    }
    public static JSONArray aList2Jarr(ArrayList al){
        return JSONObject.parseArray(JSONObject.toJSONString(al));
    }
}
