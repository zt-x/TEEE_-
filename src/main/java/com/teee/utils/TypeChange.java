package com.teee.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeChange {
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
