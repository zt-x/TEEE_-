package com.teee.service.HomeWork.Exams.Impl;

import com.alibaba.fastjson.JSONObject;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.WorkExamRule;
import com.teee.service.HomeWork.Exams.ExamService;

import java.util.ArrayList;

public class ExamServiceImpl implements ExamService {
    @Override
    public BooleanReturn setRuleForExam(int wid, WorkExamRule rule) {
        return null;
    }


    /**
     * submit:{
     *     uid:
     *     wid:
     *     facePic:
     * }
     *
     * **/
    @Override
    public BooleanReturn checkRule(JSONObject submit, ArrayList<String> rules) {
        boolean pass = true;
        String failMsg = "";
        for (String rule : rules) {
            if("FACKCHECK".equals(rule)){
                // 本地拉取用户Face

            }
        }
        return null;
    }
}
