package com.teee.service.User.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.LoginDao;
import com.teee.dao.UserFaceDao;
import com.teee.domain.LoginData;
import com.teee.domain.UserFace;
import com.teee.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    LoginDao loginDao;
    @Autowired
    UserFaceDao userFaceDao;


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

    @Override
    public boolean setFace(Long uid, String url) {
        try{
            LambdaQueryWrapper lqw = new LambdaQueryWrapper<UserFace>().eq(UserFace::getUid, uid);
            Integer num = userFaceDao.selectCount(lqw);
            if(num>0){
                userFaceDao.updateById(new UserFace(uid, url));
            }else {
                userFaceDao.insert(new UserFace(uid, url));
            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
