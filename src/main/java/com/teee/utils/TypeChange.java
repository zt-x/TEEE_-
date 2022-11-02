package com.teee.utils;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeChange {
    public static ArrayList<String> str_arrl(String str){
        if (str.equals("") || str.equals("[]") || str == null){
            return new ArrayList<>();
        }else{
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(
                    str.substring(1, str.length() - 1).split(", ")
            ));
            return arrayList;
        }

    }
    public static String arr2str(JSONArray jarr){
        String str = new String();
        ArrayList<String> as = new ArrayList<>(jarr);
        return as.toString();
    }
}
