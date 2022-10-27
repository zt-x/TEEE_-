package com.teee.service.HomeWork;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teee.domain.works.QuestionObject.QuestionObject;

/**
 * @author Xu ZhengTao
 */
public class QuestionManager {

    // 用于给作业库(BankWork)或题库(BankQuestion)添加一道题目。修改 BankWork 和 BankQuestion 类中的 questions 属性
    public static String addQuestion(String origin, QuestionObject newQusetion){
        try{
            // 解析origin -> JSONArray
            JSONArray ja;
            ja = JSONArray.parseArray(origin);
            if(ja == null){
                ja = new JSONArray();
            }
            // Put newQuestion -> JSONArray
            Object o = JSONObject.toJSON(newQusetion);
            ja.add(o);
            // 重新打包 JSONArray -> String
            return ja.toString();
        }catch (Exception e){
            return null;
        }
    }
}
