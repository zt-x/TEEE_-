package com.teee.service.publicpart;

import com.alibaba.fastjson.JSONObject;
import com.teee.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface PowerService {
    UserInfo getUser(String token);
    ArrayList<JSONObject> getRouter(String token);
    JSONObject getUserData(ArrayList<JSONObject> routers, UserInfo userInfo);
//    boolean isTokenLegal(String token);
}
