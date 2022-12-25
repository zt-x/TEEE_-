package com.teee.controller.publicpart.Work.Impl;

import com.teee.config.Code;
import com.teee.controller.publicpart.Work.BankController;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankQuestion;
import com.teee.domain.works.BankWork;
import com.teee.service.HomeWork.Questions.QuestionBankService;
import com.teee.service.HomeWork.Works.WorkBankService;
import com.teee.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
    @RequestMapping("/Bank/getWorkBankContentByID")
    @ResponseBody
    public Result getWorkBankContentByID(@RequestHeader("Authorization") String token, @RequestParam("wbid") Integer wbid) {
        BooleanReturn workBankContent = workBankService.getWorkBankContent(JWT.getUid(token),wbid);
        if(workBankContent.isSuccess()){
            return new Result(Code.Suc, workBankContent.getData(),workBankContent.getMsg());
        }else{
            return new Result(Code.ERR, null,workBankContent.getMsg());
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
    public Result addQueBank(String token, BankQuestion bankQuestion) {
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
        BooleanReturn ret = questionBankService.getQuestionBankByOnwer(JWT.getUid(token));
        if(ret.isSuccess()){
            return new Result(Code.Suc, ret.getData(),"获取成功");
        }else{
            return new Result(Code.ERR, null, ret.getMsg());
        }
    }

    @Override
    @RequestMapping("/Bank/addWorkBank")
    @ResponseBody
    public Result addWorkBank(@RequestHeader("Authorization") String token, @RequestBody BankWork bankWork) {
        Long tid = JWT.getUid(token);
        System.out.println("TID = " + tid);
        BooleanReturn ret = workBankService.createWorkBank(bankWork, tid);
        if(ret.isSuccess()){
            return new Result(Code.Suc, ret.getData(), ret.getMsg());
        }else{
            return new Result(Code.ERR, null, ret.getMsg());
        }
    }
}
