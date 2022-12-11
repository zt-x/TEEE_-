package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.dao.*;
import com.teee.domain.Course;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.*;
import com.teee.service.HomeWork.Exams.ExamService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WorkControllerImpl implements WorkController {

    @Autowired
    AWorkDao aWorkDao;
    @Autowired
    BankWorkDao bankWorkDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    WorkTimerDao workTimerDao;
    @Autowired
    ExamService examService;


    @Override
    @ResponseBody
    @RequestMapping("/Course/getAllWorksByCID")
    public Result getAllWorksByCID(@RequestParam("cid") int cid) {
        LambdaQueryWrapper<AWork> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AWork::getCid, cid);
        List<AWork> aWorks = aWorkDao.selectList(lqw);
        JSONArray jsonArray = new JSONArray();
        // 装配
        for (AWork aWork : aWorks) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(aWork);
            jsonArray.add(jsonObject);
        }
        return new Result(Code.Suc, jsonArray, "获取成功");
    }

    @ResponseBody
    @Override
    @RequestMapping("/Course/releaseAWork")
    public Result ReleaseAWork(@RequestBody AWork aWork) {


        // 验证 token和 cid
        Course course = courseDao.selectById(aWork.getCid());
        if(course == null){
            return new Result(Code.ERR, null, "课程号不存在！");
        }
        // 验证数据合法性
        // TODO
        // 写入AWorkDao数据库
        try{
            if (aWork.getDeadline().equals("")) {
                aWork.setDeadline("9999-12-30");
            }
            aWorkDao.insert(aWork);
            return new Result(Code.Suc, aWork.getId(), "发布成功!");
        }catch(Exception e){
            return new Result(Code.ERR, e.getMessage(), "Unknow Err Cause by ReleaseAWork()");
        }
    }

    @ResponseBody
    @Override
    @RequestMapping("/Work/getWork")
    public Result getWork(@RequestParam("wid") int id) {
        try{
            AWork aWork = aWorkDao.selectById(id);
            if(aWork == null){
                return new Result(Code.ERR, null,"作业不存在");
            }else{
                try{
                    BankWork bankWork = bankWorkDao.selectById(aWork.getWorkId());
                    if(bankWork == null){
                        return new Result(Code.ERR, null,"作业内容不存在");
                    }else{
                        return new Result(Code.Suc, bankWork.getQuestions(),"获取成功");
                    }
                }catch (Exception e){
                    return new Result(Code.ERR, null,"WorkContImpl.getWork Err:" + e.getMessage());
                }
            }
        }catch (Exception e){
            return new Result(Code.ERR, null,"WorkContImpl.getWork Err:" + e.getMessage());
        }
    }

    @ResponseBody
    @Override
    @RequestMapping("/Course/deleteAWork")
    public Result deleteAWork(@RequestParam("wid") Integer wid) {
        AWork aWork = aWorkDao.selectOne(new LambdaQueryWrapper<AWork>().eq(AWork::getId, wid));
        if(aWork == null){
            return new Result(Code.ERR, null, "该作业不存在(作业id异常)");
        }
        try{
            aWorkDao.deleteById(aWork.getId());
        }catch (Exception e){
            return new Result(Code.ERR, null, "奇怪的异常: " + e.getMessage());
        }
        return new Result(Code.Suc, wid, "删除成功");
    }


    @Override
    @RequestMapping("/Work/getWorkFinishStatus")
    @ResponseBody
    public Result getWorkFinishStatus(@RequestHeader("Authorization") String token,@RequestParam("cid") Integer cid) {
        Long uid = JWT.getUid(token);
        JSONArray jarr = (JSONArray) getAllWorksByCID(cid).getData();
        JSONArray jarr2 = new JSONArray();
        SubmitWorkDao submitWorkDao = SpringBeanUtil.getBean(SubmitWorkDao.class);

        // [{wid:, status: ,score:}]
        // -1 未提交
        // 0 批改中
        // 1 已完成批改
        for (Object o : jarr) {
            JSONObject jo =  (JSONObject)o;
            JSONObject jo2 = new JSONObject();
            Integer id = (Integer) jo.get("id");
            jo2.put("wid", id);
            SubmitWork submitWork = submitWorkDao.selectOne(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, id).eq(SubmitWork::getUid, uid));
            if(submitWork == null){
                // 未提交
                jo2.put("status", -1);
                jo2.put("score", 0);
            }else{
                if(submitWork.getFinishReadOver() == 0){
                    jo2.put("status", 0);
                    jo2.put("score", submitWork.getScore());
                }else{
                    jo2.put("status", 1);
                    jo2.put("score", submitWork.getScore());
                }
            }
            jarr2.add(jo2);
        }
        return new Result(Code.Suc, TypeChange.arr2str(jarr2), "获取作业完成状态成功!");
    }


    @Override
    @RequestMapping("/Work/getWorkTimer")
    @ResponseBody
    public Result getWorkTimer(@RequestHeader("Authorization") String token, Integer wid) {
        try{
            // 获取WorkTimer
            Long uid = JWT.getUid(token);
            WorkTimer workTimer = workTimerDao.selectOne(new LambdaQueryWrapper<WorkTimer>().eq(WorkTimer::getUid, uid).eq(WorkTimer::getWid, wid));
            if(workTimer == null){
                /************
                 第一次进入
                 ************/
                workTimer = new WorkTimer();
                workTimer.setUid(uid);
                workTimer.setWid(wid);
                System.out.println("UID: " + uid + "! WID: " + wid);
                AWork aWork = aWorkDao.selectOne(new LambdaQueryWrapper<AWork>().eq(AWork::getId, wid));
                if(aWork == null){
                    return new Result(Code.ERR, null, "创建Timer时错误：无法找到作业");
                }
                try{
                    Float timeLimit = aWork.getTimeLimit();
                    workTimer.setRestTime(String.valueOf(timeLimit*60.0));
                }catch (NullPointerException npe){
                    workTimer.setRestTime("无限制");
                }
                workTimerDao.insert(workTimer);
            }
            return new Result(Code.Suc, workTimer.getRestTime(), "获取Timer成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.Suc, null, "Err cause by WorkControl.getWorkTimer: " + e.getMessage());

        }

    }

    @Override
    @RequestMapping("/Exam/getExamRulePre")
    @ResponseBody
    public Result getExamRulePre(@RequestParam("wid") Integer wid) {
        try {
            JSONObject jo = new JSONObject();
            BooleanReturn ruleForExam = examService.getRuleForExam(wid);
            WorkExamRule workExamRule = (WorkExamRule) ruleForExam.getData();
            jo.put("rules",workExamRule.getRulePre());
            jo.put("text",workExamRule.getRuleText());
            return new Result(Code.Suc,jo,"获取成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, null, e.getMessage());
        }
    }

    @Override
    @RequestMapping("/Exam/getExamRuleEnter")
    @ResponseBody
    public Result getExamRuleEnter(Integer wid) {
        try {
            JSONObject jo = new JSONObject();
            BooleanReturn ruleForExam = examService.getRuleForExam(wid);
            WorkExamRule workExamRule = (WorkExamRule) ruleForExam.getData();
            jo.put("rules",workExamRule.getRuleEnter());
            return new Result(Code.Suc,jo,"获取成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, null, e.getMessage());
        }
    }

    @Override
    @RequestMapping("/Exam/setRules")
    @ResponseBody
    public Result setRules(@RequestBody WorkExamRule workExamRule) {
        try{
            examService.setRuleForExam(workExamRule);
            return new Result(Code.Suc, null, "规则添加成功");
        }catch (Exception e){
        return new Result(Code.ERR, e.getMessage(), "规则添加失败");
        }
    }
}
