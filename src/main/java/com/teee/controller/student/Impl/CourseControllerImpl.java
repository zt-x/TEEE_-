package com.teee.controller.student.Impl;

import com.alibaba.fastjson.JSON;
import com.teee.config.Code;
import com.teee.controller.student.CourseController;
import com.teee.domain.Result;
import com.teee.service.Course.CourseService;
import com.teee.service.Course.Impl.CourseServiceImpl;
import com.teee.service.User.Impl.UserServiceImpl;
import com.teee.service.User.UserService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Controller
@Transactional(rollbackFor = Exception.class)
public class CourseControllerImpl implements CourseController {

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    UserServiceImpl userService;

    @Override
    @RequestMapping("/Course/addCourse")

    @ResponseBody
    public Result addCourse(@RequestHeader("Authorization") String token, @RequestParam("cid") int cid) {
        Result r = new Result();
        // 1、 从token获取用户ID， 从Body中获取cid
        Long uid = JWT.getUid(token);
        // 2、检验用户ID、Cid合法性(是否存在)
        if(!(userService.isUserExist(uid) && courseService.isCourseExist(cid))){
            r.setCode(Code.addCourse_Fail_cidErr);
            r.setData(null);
            r.setMsg("课程号不存在或用户身份异常");
            return r;
        }
        // 3、写入CourseUser表， 根据cid读取courseUser对象，使用JSONObject读出UID、添加UID、写回
        // 4、写入UserCourse表，根据uid读 .......
        try{
            courseService.addStuToCourse(cid, uid);
            courseService.addCourseToUser(uid,cid);
            r.setCode(Code.Suc);
            r.setData(null);
            r.setMsg("添加成功！！");
        }catch (Exception e){
            r.setCode(Code.ERR);
            r.setData(null);
            r.setMsg("异常 " + e.getMessage());
            return r;
        }

        return r;
    }


    @Override
    @RequestMapping("/Course/getMyCoursesStu")
    @ResponseBody
    public Result getMyCourses(@RequestHeader("Authorization") String token) {
        Result r = new Result();
        // 1、 从token获取用户ID
        Long uid = JWT.getUid(token);
        // 2、查询
        String s = JSON.toJSONString(courseService.getStuCourses(JWT.getUid(token)));
        // 3、装配r
        r.setMsg("success");
        r.setCode(Code.Suc);
        r.setData(s);
        return r;
    }


}
