package com.teee.utils;

import com.teee.domain.LoginData;

public class Currency {
    //解析uid和role
    public static LoginData getLoginData(String token){
        Long uid = Long.valueOf(JWT.parse(token).get("uid").toString());
        String pwd = "";
        String role =  JWT.parse(token).get("role").toString();
        return new LoginData(uid, pwd, role);
    }

}
