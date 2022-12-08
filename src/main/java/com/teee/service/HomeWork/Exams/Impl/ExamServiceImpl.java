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

    @Override
    public BooleanReturn checkRule(JSONObject submit, ArrayList<String> rules) {
        boolean pass = true;
        String failMsg = "";
        for (String rule : rules) {
            if("FACKCHECK".equals(rule)){

            }else if(){

            }
        }
        return null;
    }
}
