package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.SubmitWorkController;
import com.teee.dao.*;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.SubmitWork;
import com.teee.domain.works.SubmitWorkContent;
import com.teee.service.HomeWork.SubmitService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class SubmitWorkControllerImpl implements SubmitWorkController {
    @Autowired
    SubmitService submitService;


    @Autowired
    SubmitWorkContentDao submitWorkContentDao;

    @Override
    @RequestMapping("/submit/submitWork")
    @ResponseBody

    public Result SubmitWork(@RequestHeader("Authorization") String token, @RequestParam("wid") int wid, @RequestParam("ans") String ans) {
        Result res = new Result();
        SubmitWorkContent submitWorkContent = new SubmitWorkContent();
        submitWorkContent.setSubmitContent(ans);
        submitWorkContent.setReadover("");
        submitWorkContent.setFinishReadOver(0);
        submitWorkContentDao.insert(submitWorkContent);
        Integer submitId = submitWorkContent.getSubmitId();
        SubmitWork submitWork = new SubmitWork();
        Long uid = JWT.getUid(token);
        submitWork.setUid(uid);
        submitWork.setWorkTableId(wid);
        submitWork.setScore(0F);
        submitWork.setSubmitId(submitId);
        try{
            if (submitService.submitWork(submitWork)) {
                res.setCode(Code.Suc);
                res.setMsg("提交成功!");
                res.setData(null);
            }else{
                res.setCode(Code.ERR);
                res.setMsg("提交失败!");
                res.setData(null);
            }
        }catch (Exception e){
            res.setCode(Code.ERR);
            res.setMsg("ERR on SubmitWorkController.submitWork: " + e.getMessage());
            res.setData(null);
        }
        return res;
    }

    /**
     *  获取提交的作业
     *  Role: Teacher
     *  data<JSONArray>:[
     *      {submitId:, usernmae, uid:, finish_readover: , score: }
     *  ]
     * */
    @ResponseBody
    @Override
    @RequestMapping("/submit/getAllSubmitByWorkId")
    public Result getAllSubmitByWorkId(@RequestParam("wid") int wid) {
        UserInfoDao userInfoDao = SpringBeanUtil.getBean(UserInfoDao.class);
        SubmitWorkDao submitWorkDao = SpringBeanUtil.getBean(SubmitWorkDao.class);
        LambdaQueryWrapper<SubmitWork> lqw = new LambdaQueryWrapper();
        lqw.eq(SubmitWork::getWorkTableId, wid);
        List<SubmitWork> submitWorks = submitWorkDao.selectList(lqw);
        ArrayList<JSONObject> jarr = new ArrayList<>();
        JSONObject jb;
        for (SubmitWork submitWork : submitWorks) {
            jb = (JSONObject) JSONObject.toJSON(submitWork);
            jb.put("avatar", userInfoDao.selectById(submitWork.getUid()).getAvatar());
            jarr.add(jb);
        }
        return new Result(Code.Suc, jarr, "获取成功");
    }

    @Override
    @RequestMapping("/submit/setSubmitScore")
    @ResponseBody
    public Result setSubmitScore(@RequestParam("subid") int subid, @RequestParam("score") float score) {
        return null;
    }

    @Override
    @RequestMapping("/submit/getSubmitSummary")
    @ResponseBody
    public Result getSubmitSummary(@RequestParam("wid") int wid) {
        float total_score;
        String workname;
        int submit_submitedNum;
        int submit_totalNum;
        int readOver_done;
        int readOver_total;
        // NUM of people;
        int NOP_excellent;
        int NOP_good;
        int NOP_NTB; // not too bad
        int NOP_fail;
        try{
            CourseUserDao courseUserDao = SpringBeanUtil.getBean(CourseUserDao.class);
            SubmitWorkDao submitWorkDao = SpringBeanUtil.getBean(SubmitWorkDao.class);
            AWorkDao aWorkDao = SpringBeanUtil.getBean(AWorkDao.class);
            AWork aWork = aWorkDao.selectById(wid);
            total_score = aWork.getTotalScore();
            workname = aWork.getWorkName();
            Integer cid = aWork.getCid();
            String uids = courseUserDao.selectById(cid).getUid();

            submit_totalNum = uids.length() - uids.replaceAll(",", "").length() + 1;
            submit_submitedNum = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, wid));

            readOver_done = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getFinishReadOver, 1));
            readOver_total = submit_submitedNum;

            NOP_excellent = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().between(SubmitWork::getScore, total_score*0.9, total_score));
            NOP_good = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().between(SubmitWork::getScore, total_score*0.75, total_score*0.9));
            NOP_NTB = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().between(SubmitWork::getScore, total_score*0.6, total_score*0.75));
            NOP_fail = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().between(SubmitWork::getScore, total_score*0, total_score*0.6));

            //包装 返回
            String ret = "{\"total_score\": \"" + total_score + "\", \"workname\": \"" + workname + "\", \"submit_submitedNum\": \"" + submit_submitedNum
                    + "\", \"submit_totalNum\": \"" + submit_totalNum + "\", \"readOver_done\": \"" + readOver_done + "\", \"readOver_total\": \"" + readOver_total
                    + "\", \"NOP_excellent\": \"" + NOP_excellent+ "\", \"NOP_good\": \"" + NOP_good+ "\", \"NOP_NTB\": \"" + NOP_NTB+ "\", \"NOP_fail\": \"" + NOP_fail+"\"}";

            return new Result(Code.Suc, ret, "获取提交作业信息成功捏");
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, e.getStackTrace(), "getSubmitSummaryErr"); 
        }
    }
}
