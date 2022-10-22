package com.teee.service.User.Impl;

import com.teee.dao.LoginDao;
import com.teee.domain.LoginData;
import com.teee.service.User.UserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    LoginDao loginDao;

    @Override
    public boolean isUserExist(Long uid) {
        try{
            LoginData loginData = loginDao.selectById(uid);
            if(loginData == null){
                return false;
            }
            return true;
        }catch(NullPointerException npe){
            return false;
        }
    }
}
