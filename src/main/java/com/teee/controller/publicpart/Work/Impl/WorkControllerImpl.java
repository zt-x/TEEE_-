package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.dao.AWorkDao;
import com.teee.dao.BankWorkDao;
import com.teee.dao.CourseDao;
import com.teee.domain.Course;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.BankWork;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        System.out.println(aWork);


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
}
