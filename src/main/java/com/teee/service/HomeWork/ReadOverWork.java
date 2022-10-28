package com.teee.service.HomeWork;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teee.dao.CourseDao;
import com.teee.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;


public class ReadOverWork {

    public static String AutoReadOverWork(String submitWorkContent){
        CourseDao courseDao = SpringBeanUtil.getBean(CourseDao.class);
        //解析 Content
        JSONArray content = JSONArray.parseArray(submitWorkContent);
        JSONArray back = new JSONArray();
        //自动批改 1、在题库中找到对应的题 2、比对答案 3、设置分数
        int qid;
        String ans;
        float maxScore;
        float score;
        int readOver;
        for (Object que : content) {
            JSONObject que_bak = (JSONObject) que;
            qid = (int) que_bak.get("QuestionId");
            // 获取原题
            courseDao.

        }
        //
    }
}
