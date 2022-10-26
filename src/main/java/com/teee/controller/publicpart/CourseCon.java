package com.teee.controller.publicpart;

import com.teee.config.Code;
import com.teee.controller.student.CourseController;
import com.teee.controller.teacher.TeacherCourseController;
import com.teee.domain.Result;
import com.teee.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
