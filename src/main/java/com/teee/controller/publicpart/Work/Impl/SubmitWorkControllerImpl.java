package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONArray;
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
    
    @Autowired
    SubmitWorkDao submitWorkDao;

    @Autowired
    BankWorkDao bankWorkDao;

    @Autowired
    AWorkDao aWorkDao;

    @Override
    @RequestMapping("/submit/submitWork")
    @ResponseBody

    public Result SubmitWork(@RequestHeader("Authorization") String token, @RequestParam("wid") int wid, @RequestParam("ans") String ans, @RequestParam("files") String files) {
        Result res = new Result();
        SubmitWorkContent submitWorkContent = new SubmitWorkContent();
        submitWorkContent.setSubmitContent(ans);
        submitWorkContent.setReadover("");
        submitWorkContent.setFinishReadOver(0);
        // TODO
//         files: [["", "", ""],[]]
        submitWorkContent.setFiles("".equals(files)?"[]":files);
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
    public Result setSubmitScore(@RequestParam("subid") int subid, @RequestParam("score") String score) {
        try{
            // 重置Submit_work_content表的readover
            SubmitWorkContent submitWorkContent = submitWorkContentDao.selectById(subid);
            SubmitWork submitWork = submitWorkDao.selectOne(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getSubmitId, subid));
            // 重新计算Submit_work表的Score
            int finish_readover = 1;
            float total_score = 0f;
            double factTotalScore = 0;

            ArrayList<String> arrayList = TypeChange.str2arrl("["+score+"]", ",");
            for (int i=0;i<arrayList.size();i++) {
                if(Float.parseFloat(arrayList.get(i)) == -1){
                    finish_readover = 0;
                }
                total_score += Float.parseFloat(arrayList.get(i));
                arrayList.set(i, String.format("%.2f", (Float.parseFloat(arrayList.get(i)))));
            }
            JSONArray workCotent = SubmitWork.getWorkCotent(submitWork);
            com.alibaba.fastjson2.JSONObject jo;
            for (int i=0; i<workCotent.size(); i++) {
                jo = (com.alibaba.fastjson2.JSONObject) workCotent.get(i);
                Float qscore = Float.valueOf(jo.get("qscore").toString());
                factTotalScore+=qscore;
            }

            submitWorkContent.setReadover(TypeChange.arrL2str(arrayList));
            double rate = aWorkDao.selectById(submitWork.getWorkTableId()).getTotalScore() / factTotalScore;
            submitWork.setScore((float) (total_score*rate));
            submitWork.setFinishReadOver(finish_readover);
            submitWorkContent.setFinishReadOver(finish_readover);
            submitWorkContentDao.updateById(submitWorkContent);

            submitWorkDao.updateById(submitWork);
            System.out.println("subworkID=" + submitWork.getId() + "被改变了");
            return new Result(Code.Suc, null, "批改成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, e.getStackTrace(), "Err cause by submWCPmImpl.setSubScore");
        }
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

            NOP_excellent = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, wid).between(SubmitWork::getScore, total_score*0.9, total_score));
            NOP_good = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, wid).between(SubmitWork::getScore, total_score*0.75, total_score*0.9-0.0001));
            NOP_NTB = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, wid).between(SubmitWork::getScore, total_score*0.6, total_score*0.75-0.0001));
            NOP_fail = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, wid).between(SubmitWork::getScore, total_score*0, total_score*0.6-0.0001));

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

    @Override
    @RequestMapping("/submit/getSubmitBySid")
    @ResponseBody
    public Result getSubmitBySid(@RequestParam("sid") int sid) {
        try{

            SubmitWorkContent submitWorkContent = submitWorkContentDao.selectById(sid);

            // 给Content的每一项加上引号
            ArrayList<String> arrayList = TypeChange.str2arrl(submitWorkContent.getSubmitContent(), ", ");
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.set(i, "\"" + arrayList.get(i) + "\"");
            }
            submitWorkContent.setSubmitContent(arrayList.toString());

            // 给Files的每一项加上引号
            ArrayList<String> arrayList2 = TypeChange.str2arrl(submitWorkContent.getFiles(), ", ");
            for (int i = 0; i < arrayList2.size(); i++) {
                arrayList2.set(i, "\"" + arrayList2.get(i) + "\"");
            }
            submitWorkContent.setFiles(arrayList2.toString());

            return new Result(Code.Suc, JSONObject.toJSONString(submitWorkContent), "获取sid=" + sid + "的数据成功");
        }catch (Exception e){
            return new Result(Code.ERR, null, "Err cause by getSubmitBySid: " + e.getMessage());
        }
    }

    @Override
    @RequestMapping("/submit/getSubmitByWorkId")
    @ResponseBody
    public Result getSubmitByWorkId(@RequestHeader("Authorization") String token,@RequestParam("wid") int wid) {
        try{
            Long uid = JWT.getUid(token);
            SubmitWork submitWork = submitWorkDao.selectOne(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getUid, uid).eq(SubmitWork::getWorkTableId, wid));
            return new Result(Code.Suc, JSONObject.toJSONString(submitWork), "获取成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR, null, "Err Cause by getSByWid: " + e.getStackTrace());
        }
    }
}
