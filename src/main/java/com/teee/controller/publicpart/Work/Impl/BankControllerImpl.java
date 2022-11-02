package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.BankController;
import com.teee.dao.BankQuestionDao;
import com.teee.dao.BankWorkDao;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankQuestion;
import com.teee.domain.works.BankWork;
import com.teee.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller

public class BankControllerImpl implements BankController {

    @Autowired
    BankQuestionDao bankQuestionDao;

    @Autowired
    BankWorkDao bankWorkDao;

    @Override
    @RequestMapping("/Bank/getQueBankByTid")
    @ResponseBody
    public Result getQueBankByTid(@RequestHeader("Authorization") String token) {
        Long tid = JWT.getUid(token);
        Result res = new Result();
        JSONArray jarr = new JSONArray();
        LambdaQueryWrapper<BankQuestion> lqw = new LambdaQueryWrapper<>();
        lqw.eq(BankQuestion::getOwner, tid);
        try{
            List<BankQuestion> bankQuestions = bankQuestionDao.selectList(lqw);
            if(bankQuestions.size() > 0){
                for (BankQuestion bankQuestion : bankQuestions) {
                    JSONObject o = (JSONObject) JSONObject.toJSON(bankQuestion);
                    jarr.add(o);
                }
                res.setCode(Code.Bank_getQueSucc);
                res.setMsg("获取成功");
                ArrayList<String> as = new ArrayList<>(jarr);
                res.setData(as);
            }else{
                res.setCode(Code.Bank_noQue);
                res.setMsg("列表为空");
                res.setData(null);
            }
        }catch(Exception e){
            res.setCode(Code.ERR);
            res.setMsg("【BankControllerErr】Err: " + e.getMessage());
            res.setData(null);
        }
        return res;
    }

    @Override
    @RequestMapping("/Bank/addWorkBank")
    @ResponseBody
    public Result addWorkBank(@RequestHeader("Authorization") String token, @RequestBody BankWork bankWork) {
        Long tid = JWT.getUid(token);
        Result r = new Result();
        bankWork.setOwner(tid);
        try{
            bankWorkDao.insert(bankWork);
            r.setCode(Code.Suc);
            r.setData(bankWork.getWorkId());
            r.setMsg("添加成功");
        }catch(Exception e){
            r.setCode(Code.ERR);
            r.setData(null);
            r.setMsg("添加失败");
        }
        return r;
    }
}
