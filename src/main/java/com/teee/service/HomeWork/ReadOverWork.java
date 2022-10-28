package com.teee.service.HomeWork;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teee.domain.returnClass.ReadOverWorkReturn;
import com.teee.domain.works.SubmitWork;

public class ReadOverWork {
    public static ReadOverWorkReturn AutoReadOverWork(SubmitWork submitWork){
        // {总体情况:{userName: xxx, userId: uid, totalScore: xxxx, unReadOver: xxx}, 各个题目情况:[{score: ,autoFlag: 1},{},{} .....]}
        JSONObject readOverWorkReturn = new JSONObject();
        JSONObject totalStatus = new JSONObject();
        JSONArray questionsStatus = new JSONArray();

    }
}
