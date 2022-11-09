package com.teee.service.publicpart.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.UserInfoDao;
import com.teee.domain.LoginData;
import com.teee.domain.UserInfo;
import com.teee.service.publicpart.PowerService;
import com.teee.utils.Currency;
import com.teee.utils.JWT;
import com.teee.utils.RouterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    UserInfoDao userInfoDao;


    @Override
    public UserInfo getUser(String token) {
        LoginData loginData = Currency.getLoginData(token);
        Long uid = loginData.getUid();
        String role = loginData.getRole();
        LambdaQueryWrapper<UserInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserInfo::getUid, uid);
        UserInfo correctUserInfo = userInfoDao.selectOne(lqw);
        correctUserInfo.setRole(role);
        return correctUserInfo;
    }

    @Override
    public ArrayList<JSONObject> getRouter(String role) {
        RouterFactory rf = new RouterFactory();
        ArrayList<JSONObject> routers = new ArrayList<>();
        if("admin".equals(role)){
            routers.add(rf.getRouterObject("我是Admin", "/CourseAdmin", "CourseView.vue", "fa fa-camera", true));
            routers.add(rf.getRouterObject("Admin特有的统计数据", "/StatisticsAdmin", "StatisticsView.vue", "fa fa-camera", true));
            routers.add(rf.getRouterObject("CourseContent", "/CourseContent", "CourseContent.vue", "", false));
        }else if("student".equals(role)){
            routers.add(rf.getRouterObject("我的课程", "/Course", "CourseView.vue", "fas fa fa-list-alt", true));
            routers.add(rf.getRouterObject("统计数据", "/Statistics", "StatisticsView.vue", "fas fa-bar-chart", true));
            routers.add(rf.getRouterObject("CourseContent", "/CourseContent", "CourseContent.vue", "", false));
        }else if("teacher".equals(role)){
            routers.add(rf.getRouterObject("我的课程", "/Course", "CourseView.vue", "fas fa fa-list-alt", true));
            routers.add(rf.getRouterObject("CourseContent", "/CourseContent", "CourseContent.vue", "", false));
            routers.add(rf.getRouterObject("WorkContent", "/WorkContent", "Course/AWorkContent.vue", "", false));
            routers.add(rf.getRouterObject("作业库/题库管理", "/BankManager", "BankManagerView.vue", "fa fa-university", true));

        }
        return routers;
    }
    /*
*    data:
*      {
*         username: username,
*         role: role,
*         routers: [
*           {name:xxx, }
*        ]
*      }
    * */
    @Override
    public JSONObject getUserData(ArrayList<JSONObject> routers, UserInfo userInfo) {
        JSONObject userData = new JSONObject();
        userData.put("username", userInfo.getUsername());
        userData.put("role", userInfo.getRole());
        userData.put("routers", getRouter(userInfo.getRole()).toString());
        userData.put("avatar", userInfo.getAvatar());
        return userData;
    }


    public static boolean isTokenLegal(String token) {
        try{
            JWT.parse(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
