package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.SubmitWorkController;
import com.teee.dao.SubmitWorkContentDao;
import com.teee.dao.SubmitWorkDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.SubmitWork;
import com.teee.domain.works.SubmitWorkContent;
import com.teee.service.HomeWork.SubmitService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
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
        System.out.println(ans);
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
        SubmitWorkDao submitWorkDao = SpringBeanUtil.getBean(SubmitWorkDao.class);
        LambdaQueryWrapper<SubmitWork> lqw = new LambdaQueryWrapper();
        lqw.eq(SubmitWork::getWorkTableId, wid);
        List<SubmitWork> submitWorks = submitWorkDao.selectList(lqw);
        ArrayList<String> jarr = new ArrayList<>();
        for (SubmitWork submitWork : submitWorks) {
            jarr.add(JSONObject.toJSONString(submitWork));
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
    public Result getSubmitSummary(@RequestParam("subid") int subid) {
        return null;
    }
}
