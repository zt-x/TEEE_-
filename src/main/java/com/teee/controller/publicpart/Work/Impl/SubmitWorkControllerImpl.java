package com.teee.controller.publicpart.Work.Impl;

import com.teee.config.Code;
import com.teee.controller.publicpart.Work.SubmitWorkController;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.SubmitWork;
import com.teee.service.HomeWork.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SubmitWorkControllerImpl implements SubmitWorkController {

    @Autowired
    SubmitService submitService;

    @Override
    @RequestMapping("/submit/submitWork")
    @ResponseBody
    public Result SubmitWork(@RequestBody SubmitWork submitWork) {
        Result res = new Result();
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

    @Override
    @RequestMapping("/submit/getAllSubmitByWorkId")
    @ResponseBody
    public Result getAllSubmitByWorkId(@RequestParam("wid") int wid) {
        Result res = new Result();

        return null;
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
