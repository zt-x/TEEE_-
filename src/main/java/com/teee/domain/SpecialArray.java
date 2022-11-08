package com.teee.domain;

import java.util.ArrayList;

public class SpecialArray {
    private String separator = "_-`_-`_-`-_-`-_";
    private String content = "";
    public void add(Object obj){
        if("".equals(content)){
            content += obj.toString();
        }else{
            content += separator + obj.toString();
        }
    }
    public int size(){
        int count = 0;
        String[] arr = content.split(separator);
        int len = arr.length;
        if(content.endsWith(separator)) {
            //如果子串在末尾
            len ++;
        }
        count = len;
        return count;
    }
    public static SpecialArray ArrayList2SpecialArray(ArrayList arrayList, String separator){
        SpecialArray specialArray = new SpecialArray();
        for (Object o : arrayList) {
            specialArray.add(o);
        }
        return specialArray;
    }

    @Override
    public String toString() {
        return content;
    }
}
