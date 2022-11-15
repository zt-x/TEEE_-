package com.teee.controller.publicpart;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.student.CourseController;
import com.teee.controller.teacher.TeacherCourseController;
import com.teee.dao.AWorkDao;
import com.teee.dao.CourseDao;
import com.teee.dao.CourseUserDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.Course;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CourseCon {

    @Autowired
    CourseController courseController;
    @Autowired
    TeacherCourseController teacherCourseController;

    @RequestMapping("/Course/getMyCourse")
    @ResponseBody
    public Result getMyCourse(@RequestHeader("Authorization") String token){
        if(JWT.getRole(token).equals("teacher")){
            return teacherCourseController.getMyCourses(token);
        }else if(JWT.getRole(token).equals("student")){
            return courseController.getMyCourses(token);
        }else{
            return new Result(Code.ERR,null, "身份错误");
        }
    }


    @Autowired
    CourseDao courseDao;
    @Autowired
    CourseUserDao courseUserDao;
    @Autowired
    AWorkDao aWorkDao;
    @Autowired
    UserInfoDao userInfoDao;
    /**
     *
     * Result :{
     *     CourseName:, TeacherName:, UserCount:, WorkCount:,ExamsConut:, CourseTime:
     * }
     *
     */
    @RequestMapping("/Course/getCourseInfo")
    @ResponseBody
    public Result getCourseInfo(@RequestParam Integer cid){
        String CourseName;
        String TeacherName;
        Integer UserCount;
        Integer WorkCount;
        Integer ExamsCount;
        String CourseTime;

        Course course = courseDao.selectById(cid);
        if(course == null){
            return new Result(Code.ERR, "course == null", "Err Cause By getCourseInfo");
        }
        try{
            CourseName = course.getCourseName();
            TeacherName = userInfoDao.selectById(course.getTid()).getUsername();
            UserCount = TypeChange.str2arrl(courseUserDao.selectById(cid).getUid()).size();
            WorkCount = aWorkDao.selectCount(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, cid).eq(AWork::getIsExam, 0));
            CourseTime = course.getStartTime() + " - " + course.getEndTime();
            ExamsCount = aWorkDao.selectCount(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, cid).eq(AWork::getIsExam, 1));
            JSONObject jo = new JSONObject();
            jo.put("CourseName", CourseName);
            jo.put("TeacherName", TeacherName);
            jo.put("UserCount", UserCount);
            jo.put("WorkCount", WorkCount);
            jo.put("CourseTime", CourseTime);
            jo.put("ExamsCount", ExamsCount);
            return new Result(Code.Suc, jo, "获取课程信息成功");

        }catch (Exception e){
            return new Result(Code.ERR, e.getStackTrace(), "获取课程信息失败");
        }


    }
}
