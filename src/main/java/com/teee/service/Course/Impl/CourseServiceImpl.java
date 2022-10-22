package com.teee.service.Course.Impl;

import com.alibaba.fastjson.JSONObject;
import com.teee.config.Code;
import com.teee.dao.CourseUserDao;
import com.teee.dao.UserCourseDao;
import com.teee.domain.CourseUser;
import com.teee.domain.UserCourse;
import com.teee.service.Course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseUserDao courseUserDao;
    @Autowired
    UserCourseDao userCourseDao;

    @Override
    public boolean isCourseExist(int cid){
        try{
            CourseUser cu = courseUserDao.selectById(cid);
            if(cu == null){
                return false;
            }
            return true;
        }catch(NullPointerException npe){
            return false;
        }
    }

    @Override
    public Integer addStuToCourse(int cid, Long uid) {
        int new_ = 0;
        try{

            CourseUser courseUser = courseUserDao.selectById(cid);
            if(courseUser == null){
                courseUser = new CourseUser(cid, "[]");
                new_ = 1;
            }
            ArrayList<Long> uids = new ArrayList<Long>();
            String[] split = courseUser.getUid().replace("[", "").replace("]", "").split(",");
            if(!split[0].equals("")){
                for (String s : split) {
                    uids.add(Long.valueOf(s.trim()));
                }
            }
            if(!uids.contains(uid)){
                uids.add(Long.valueOf(uid));
            }
            courseUser.setUid(uids.toString());
            System.out.println(courseUser);
            if(new_ == 1){
                courseUserDao.insert(courseUser);
            }else{
                courseUserDao.updateById(courseUser);
            }
            return Code.Suc;
        }catch (Exception e){
            return Code.ERR;
        }
    }

    @Override
    public Integer addCourseToUser(Long uid, int cid) {
        int new_ = 0;
        try{
            UserCourse userCourse =  userCourseDao.selectById(uid);
            if(userCourse == null){
                userCourse = new UserCourse(uid, "[]");
                new_ = 1;
            }
            ArrayList<Integer> cids = new ArrayList<>();

            String[] split = userCourse.getCid().replace("[", "").replace("]", "").split(",");
            if(!split[0].equals("")){
                for (String s : split) {
                    cids.add(Integer.valueOf(s.trim()));
                }
            }
            if(!cids.contains(cid)){
                cids.add(cid);
            }
            userCourse.setCid(cids.toString());
            if(new_ == 1){
                userCourseDao.insert(userCourse);
            }else{
                userCourseDao.updateById(userCourse);
            }
            return Code.Suc;
        }catch (Exception e){
            return Code.ERR;
        }

    }
}
