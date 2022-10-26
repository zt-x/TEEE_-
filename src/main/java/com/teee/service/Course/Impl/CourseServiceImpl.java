package com.teee.service.Course.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teee.config.Code;
import com.teee.dao.CourseDao;
import com.teee.dao.CourseUserDao;
import com.teee.dao.UserCourseDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.Course;
import com.teee.domain.CourseUser;
import com.teee.domain.TeacherCourse;
import com.teee.domain.UserCourse;
import com.teee.service.Course.CourseService;
import com.teee.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private UserCourseDao userCourseDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public boolean isCourseExist(int cid){
        try{
            Course cu = courseDao.selectById(cid);
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


    @Override
    public JSONArray getStuCourses(Long uid) {
        JSONArray courses = new JSONArray();
        Course course;
        JSONObject course_json = new JSONObject();
        try{
            UserCourse userCourse = userCourseDao.selectById(uid);
            String[] cids = userCourse.getCid().replace("[", "").replace("]", "").split(",");
            for (String cid : cids) {
                cid = cid.replaceAll(" ", "");
                course = courseDao.selectById(Integer.valueOf(cid));
                course_json = (JSONObject) JSONObject.toJSON(course);
                course_json.put("Name", course.getCourseName());
                course_json.put("id", course.getCourseName());
                course_json.put("TeacherName", userInfoDao.selectById(course.getTid()).getUsername());
                course_json.put("College", course.getCollege());
                course_json.put("Time", course.getStartTime() + " - " + course.getEndTime());
                course_json.put("IMG", course.getBanner());
                course_json.put("status", course.getStatus());
                courses.add(course_json);
            }
        }catch(NullPointerException npe){
        }
        return courses;
    }

    @Override
    public JSONArray getTeaCourses(Long tid) {
        JSONArray courses = new JSONArray();
        Course course;
        JSONObject course_json;
        for (Integer cid : courseDao.getCidByTid(tid)) {
            course = courseDao.selectById(cid);
            course_json = (JSONObject) JSONObject.toJSON(course);
            course_json.put("Name", course.getCourseName());
            course_json.put("id", course.getCourseName());
            course_json.put("TeacherName", userInfoDao.selectById(course.getTid()).getUsername());
            course_json.put("College", course.getCollege());
            course_json.put("Time", course.getStartTime() + " - " + course.getEndTime());
            course_json.put("IMG", course.getBanner());
            course_json.put("status", course.getStatus());
            courses.add(course_json);
        }
        return courses;
    }



}
