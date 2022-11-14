package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.dao.AWorkDao;
import com.teee.dao.BankWorkDao;
import com.teee.dao.CourseDao;
import com.teee.dao.SubmitWorkDao;
import com.teee.domain.Course;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.BankWork;
import com.teee.domain.works.SubmitWork;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkControllerImpl implements WorkController {

    @Autowired
    AWorkDao aWorkDao;
    @Autowired
    BankWorkDao bankWorkDao;
    @Autowired
    CourseDao courseDao;

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
            aWorkDao.insert(aWork);
        }catch(Exception e){
            return new Result(Code.ERR, e.getMessage(), "Unknow Err Cause by ReleaseAWork()");
        }
        return new Result(Code.Suc, null, "发布成功!");
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
}
