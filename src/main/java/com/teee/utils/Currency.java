package com.teee.utils;

import com.teee.domain.LoginData;

import java.util.ArrayList;

public class Currency {
    //解析uid和role
    public static LoginData getLoginData(String token){
        Long uid = Long.valueOf(JWT.parse(token).get("uid").toString());
        String pwd = "";
        String role =  JWT.parse(token).get("role").toString();
        return new LoginData(uid, pwd, role);
    }

//    public static <T> ArrayList<T> str2ArrList(String str, T t){
//        ArrayList<T> arr = new ArrayList<>();
//        str.replace("[", "").replace("]", "");
//        String[] split = str.split(",");
//        for (String s : split) {
//            arr.add((T) Long.valueOf(s));
//        }
//
//    }
}
