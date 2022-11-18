package com.teee.controller.publicpart;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.student.CourseController;
import com.teee.controller.teacher.TeacherCourseController;
import com.teee.dao.*;
import com.teee.domain.Course;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.SubmitWork;
import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
    /**
     * Return:{
     *     [优秀, 良好, 及格, 不及格],
     *     [
     *      {WorkName: , score: },
     *      {WorkName: , score: }
     *     ]
     * }
     * */

    @Autowired
    SubmitWorkDao submitWorkDao;

    @RequestMapping("/Course/getCourseStatistic")
    @ResponseBody
    public Result getCourseStatistic(@RequestHeader("Authorization") String token, @RequestParam("cid") Integer cid){
        try{
            JSONObject ret = new JSONObject();
            // 获取最后一次考试ID
            List<AWork> objects = aWorkDao.selectList(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, cid));
            int max = -1;
            for (AWork object : objects) {
                if(object.getWorkId() > max && object.getIsExam() == 1){
                    max = object.getWorkId();
                }
            }
            int excellent = 0;
            int good = 0;
            int NTB = 0;
            int fail = 0;
            Float total_score = aWorkDao.selectById(max).getTotalScore();
            if(max != -1){
                List<SubmitWork> submitWorks = submitWorkDao.selectList(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, max));
                for(SubmitWork sw: submitWorks){
                    if(sw.getScore() >= 0.9*total_score){
                        excellent++;
                    }else if(sw.getScore() >= 0.75*total_score){
                        good++;
                    }else if(sw.getScore() >= 0.6*total_score){
                        NTB++;
                    }else {
                        fail++;
                    }
                }
            }
            // 获取考试成绩

            ArrayList<Integer> arr = new ArrayList<>();

            arr.add(0, excellent);
            arr.add(1, good);
            arr.add(2, NTB);
            arr.add(3, fail);
            ret.put("examsCount", arr);

            // 获取历次作业统计
            //  获取role
            String role = JWT.getRole(token);
            Long uid = JWT.getUid(token);
            //  获取历次作业
            JSONArray scores = new JSONArray();
            if(role.equals("teacher")){
                // 获取历次作业的平均成绩
                for (AWork object : objects) {
                    if(object.getIsExam() == 0){
                        List<SubmitWork> submitWorks1 = submitWorkDao.selectList(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, object.getId()));
                        float avarage_score = 0;
                        for (SubmitWork submitWork : submitWorks1) {
                            avarage_score += submitWork.getScore();
                        }
                        avarage_score /= (submitWorks1.size() == 0?1:submitWorks1.size());
                        JSONObject jo = new JSONObject();
                        jo.put("WorkName", object.getWorkName());
                        jo.put("score", avarage_score);
                        scores.add(jo);
                    }
                }
            }else if(role.equals("student")){
                // 获取历次作业的成绩
                for (AWork object : objects) {
                    if(object.getIsExam() == 0){
                        SubmitWork submitWorks1 = submitWorkDao.selectOne(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getUid, uid).eq(SubmitWork::getWorkTableId, object.getId()));
                        JSONObject jo = new JSONObject();
                        jo.put("WorkName", object.getWorkName());
                        jo.put("score", submitWorks1 == null?"0":submitWorks1.getScore());
                        scores.add(jo);
                    }
                }
            }else {
                System.out.println("Role Err: " + role);
            }
            ret.put("worksCount", TypeChange.arr2str(scores));
            return  new Result(Code.Suc, ret, "获取成功嘞！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, e.getMessage(), "Err Cause by getCourseStatistic" + e.getMessage());
        }
    }


    /**
     * Result:{
     *     submit_totalNum:
     *     works:[
     *          {
     *              wid:
     *              subNum:
     *              rDone:
     *          }
     *     ]
     *     
     * }
     * */
    @RequestMapping("/Course/getAllWorkSummary")
    @ResponseBody
    public Result getAllWorkSummary(@RequestParam("cid") Integer cid){
        try{
            JSONObject ret = new JSONObject();
            String uids = courseUserDao.selectById(cid).getUid();
            int submit_totalNum = uids.length() - uids.replaceAll(",", "").length() + 1;
            ret.put("submit_totalNum", submit_totalNum);
            ArrayList<JSONObject> arrayList = new ArrayList<>();
            List<AWork> aWorks = aWorkDao.selectList(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, cid));
            for (AWork aWork : aWorks) {
                int readOver_done = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getFinishReadOver, 1).eq(SubmitWork::getWorkTableId,aWork.getId()));
                int submit_submitedNum = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, aWork.getId()));
                JSONObject jo = new JSONObject();
                jo.put("wid", aWork.getId());
                jo.put("subNum", submit_submitedNum);
                jo.put("rDone", readOver_done);
                arrayList.add(jo);
            }
            ret.put("works", arrayList);
            return new Result(Code.Suc, ret, "获取成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, null, "Err Cause by CourseCon.getAllWorkSummary: " + e.getStackTrace());
        }
    }
}
