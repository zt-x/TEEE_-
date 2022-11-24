package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.controller.publicpart.Work.BankController;
import com.teee.dao.BankOwnerDao;
import com.teee.dao.BankQuestionDao;
import com.teee.dao.BankWorkDao;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankOwner;
import com.teee.domain.works.BankQuestion;
import com.teee.domain.works.BankWork;
import com.teee.service.HomeWork.Questions.QuestionBankService;
import com.teee.service.HomeWork.Works.WorkBankService;
import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller

public class BankControllerImpl implements BankController {

    @Autowired
    WorkBankService workBankService;
    @Autowired
    QuestionBankService questionBankService;


    @Override
    @RequestMapping("/Bank/getWorkBankByTid")
    @ResponseBody
    /**
     * return:[
     *  {
     *      id:
     *      owner:
     *      BankName:
     *      Tags:["","",""]
     *
     *  }
     * ]
     * */
    public Result getWorkBankByTid(@RequestHeader("Authorization") String token) {
        BooleanReturn ret = workBankService.getWorkBankByOnwer(JWT.getUid(token));
        if(ret.isSuccess()){
            return new Result(Code.Suc, ret.getData(),"获取成功");
        }else{
            return new Result(Code.ERR, null, ret.getMsg());
        }
    }

    @Override
    public Result editWorkBank(BankWork bankWork) {
        return null;
    }

    @Override
    public Result deleteWorkBank(Integer wbid) {
        return null;
    }

    @Override
    public Result addWorkBank(String token, BankQuestion bankQuestion) {
        return null;
    }

    @Override
    public Result editQueBank(BankQuestion bankQuestion) {
        return null;
    }

    @Override
    public Result deleteQueBank(Integer qbid) {
        return null;
    }


    @Override
    @RequestMapping("/Bank/getQueBankByTid")
    @ResponseBody
    public Result getQueBankByTid(@RequestHeader("Authorization") String token) {
        BooleanReturn ret = workBankService.getWorkBankByOnwer(JWT.getUid(token));
        if(ret.isSuccess()){
            return new Result(Code.Suc, ret.getData(),"获取成功");
        }else{
            return new Result(Code.ERR, null, ret.getMsg());
        }

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
            // 注册到BankOwner表
            try{
                BankOwner bankOwner = bankOwnerDao.selectOne(new LambdaQueryWrapper<BankOwner>().eq(BankOwner::getOid, tid));
                if(bankOwner != null){
                    String bids = bankOwner.getBids();
                    ArrayList<String> arrayList = TypeChange.str2arrl(bids);
                    arrayList.add(bankWork.getWorkId().toString());
                    bankOwner.setBids(TypeChange.arrL2str(arrayList));
                    bankOwnerDao.updateById(bankOwner);
                }else{
                    bankOwner = new BankOwner();
                    bankOwner.setOid(tid);
                    bankOwner.setBids("[]");
                    bankOwnerDao.insert(bankOwner);
                }
                r.setCode(Code.Suc);
                r.setData(bankWork.getWorkId());
                r.setMsg("添加成功");
            }catch (Exception e){
                e.printStackTrace();
                r.setCode(Code.ERR);
                r.setData(null);
                r.setMsg("添加失败");
            }

        }catch(Exception e){
            r.setCode(Code.ERR);
            r.setData(null);
            r.setMsg("添加失败");
        }
        return r;
    }
}
