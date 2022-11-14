package com.teee.controller.teacher.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.teee.config.Code;
import com.teee.controller.teacher.TeacherCourseController;
import com.teee.dao.CourseDao;
import com.teee.dao.CourseUserDao;
import com.teee.domain.Course;
import com.teee.domain.CourseUser;
import com.teee.domain.returnClass.Result;
import com.teee.service.Course.CourseService;
import com.teee.service.User.UserService;
import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;


@Controller
public class TeacherCourseControllerImpl implements TeacherCourseController {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Override
    @RequestMapping("/Course/createCourse")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result createCourse(@RequestHeader("Authorization") String token, @RequestBody Course courseInfo) {
        Result r = new Result();
        // 1、 从token获取用户ID， 从Body中获取cid
        Long tid = JWT.getUid(token);
        // 2、检验用户ID合法性(是否存在)
        if(!(userService.isUserExist(tid))){
            r.setCode(Code.addCourse_Fail_cidErr);
            r.setData(null);
            r.setMsg("用户身份异常");
            return r;
        }
        // 3、写Course表\写teacher_course表\ 写Course_User表
        try{
            courseInfo.setTid(tid);
            if (courseInfo.getBanner().equals("")) {
                courseInfo.setBanner("/pure_brown.png");
            }
            courseDao.insert(courseInfo);
            courseDao.insertCourseToTeahcer(courseInfo.getId(), tid);
            courseUserDao.insert(new CourseUser(courseInfo.getId(),""));
            r.setCode(Code.Suc);
            r.setData(null);
            r.setMsg("创建成功");
        }catch (Exception e){
            r.setCode(Code.ERR);
            r.setData(null);
            r.setMsg("异常 " + e.getMessage());
            return r;
        }

        return r;
    }



    @Override
    @RequestMapping("/Course/getMyCourseTea")
    @ResponseBody
    public Result getMyCourses(@RequestHeader("Authorization") String token) {
        Result r = new Result();
        // 1、 从token获取用户ID
        Long tid = JWT.getUid(token);

        // 2、查询
        String s = JSON.toJSONString(courseService.getTeaCourses(tid));
        // 3、装配r
        r.setMsg("success");
        r.setCode(Code.Suc);
        r.setData(s);
        return r;

    }

    @Override
    @RequestMapping("/Course/getAllUser")
    @ResponseBody
    /**
     *
     * Return:
     *  {
     *      [uid:, username: ,avatar:, finishWorkNum:, workAverageScore:]
     *  }
     *
     * */
    public Result getAllUser(Integer cid) {
        String uids = courseUserDao.selectById(cid).getUid();
        ArrayList<String> arrayList = TypeChange.str2arrl(uids);
        JSONArray jarr = new JSONArray();
        for (String s : arrayList) {

        }
        if(uids != null){
            return new Result(Code.Suc, uids, "获取用户成功");

        }else{
            return new Result(Code.ERR, null, "获取");
        }
    }
}
