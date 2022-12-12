package com.teee.service.HomeWork.Exams;

import com.alibaba.fastjson2.JSONObject;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.WorkExamRule;

import java.util.ArrayList;



public interface ExamService {

    BooleanReturn setRuleForExam(WorkExamRule rule);
    BooleanReturn getRuleForExam(int wid);
    /**
     * Submit:{
     *     uid:
     *     wid:
     *     subFace:
     * }
     **/
    BooleanReturn checkRule(JSONObject submit, ArrayList<String> rules);
    BooleanReturn faceCheck(Long uid, String imgUrl);

}
