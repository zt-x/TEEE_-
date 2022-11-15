package com.teee.controller.teacher.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.teacher.TeacherCourseController;
import com.teee.dao.*;
import com.teee.domain.Course;
import com.teee.domain.CourseUser;
import com.teee.domain.UserInfo;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.SubmitWork;
import com.teee.service.Course.CourseService;
import com.teee.service.User.UserService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


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
     * */
    // TODO
    public Result getAllUser(Integer cid) {
        UserInfoDao userInfoDao = SpringBeanUtil.getBean(UserInfoDao.class);
        SubmitWorkDao submitWorkDao = SpringBeanUtil.getBean(SubmitWorkDao.class);
        AWorkDao aWorkDao = SpringBeanUtil.getBean(AWorkDao.class);
        List<AWork> aWorks = aWorkDao.selectList(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, cid));
        List<Integer> wids = new ArrayList<>();
        for (AWork aWork : aWorks) {
            wids.add(aWork.getWorkId());
        }
        String uids = courseUserDao.selectById(cid).getUid();
        if(uids != null){
            ArrayList<String> arrayList = TypeChange.str2arrl(uids);
            JSONArray jarr = new JSONArray();
            // 遍历学生
            for (String s : arrayList) {
                UserInfo userInfo = userInfoDao.selectById(Long.valueOf(s));
                JSONObject ret = new JSONObject();
                if(userInfo == null){
                    userInfo = new UserInfo();
                }
                ret.put("uid", userInfo.getUid());
                ret.put("username", userInfo.getUsername());
                ret.put("avatar", userInfo.getAvatar());
                ret.put("finishWorkNum", submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getUid, userInfo.getUid())));
                List<SubmitWork> submitWorks = submitWorkDao.selectList(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getUid, userInfo.getUid()));
                float avarage = 0;
                for (SubmitWork submitWork : submitWorks) {
                    avarage += submitWork.getScore();
                }
                ret.put("workAverageScore", avarage);
                jarr.add(ret);
            }
            return new Result(Code.Suc, TypeChange.arr2str(jarr), "获取用户成功");
        }else{
            return new Result(Code.ERR, null, "获取");
        }
    }
}
