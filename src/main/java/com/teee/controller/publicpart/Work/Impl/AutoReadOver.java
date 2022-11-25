package com.teee.controller.publicpart.Work.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.teee.config.Code;
import com.teee.dao.AWorkDao;
import com.teee.dao.BankWorkDao;
import com.teee.dao.SubmitWorkContentDao;
import com.teee.dao.SubmitWorkDao;
import com.teee.domain.works.SubmitWork;
import com.teee.domain.works.SubmitWorkContent;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AutoReadOver {
    @Async
    public SubmitWork autoReadOver(SubmitWork submitWork, boolean readChoice, boolean readFillIn) {
        System.out.println("进入AutoReadOver");
        SubmitWorkContentDao submitWorkContentDao = SpringBeanUtil.getBean(SubmitWorkContentDao.class);
        AWorkDao aWorkDao = SpringBeanUtil.getBean(AWorkDao.class);
        SubmitWorkDao submitWorkDao = SpringBeanUtil.getBean(SubmitWorkDao.class);
        SubmitWork sw = submitWork;
        Integer submitId = sw.getSubmitId();
        SubmitWorkContent submitWorkContent = submitWorkContentDao.selectById(submitId);
        ArrayList<String> readOver;
        ArrayList<String> submitContent = TypeChange.str2arrl(submitWorkContent.getSubmitContent());
        String readover = submitWorkContent.getReadover();
        double factTotalScore = 0;
        if(readover.equals("")){
            int len = SubmitWork.getNumOfQue(sw);
            readOver = new ArrayList<>(len);
            for(int i=0; i<len; i++){
                readOver.add(i,"");
            }
        }else {
            readOver = TypeChange.str2arrl(readover);
        }
        JSONArray workCotent = SubmitWork.getWorkCotent(sw);
        JSONObject jo;
        if (workCotent != null) {
            for (int i=0;i<workCotent.size();i++) {
                jo = (JSONObject) workCotent.get(i);
                Float qscore = Float.valueOf(jo.get("qscore").toString());
                factTotalScore+=qscore;
                // 选择题
                if (jo.get("qtype").equals(Code.QueType_choice_question)) {
                    Float score = -1f;
                    ArrayList<String> cans = TypeChange.str2arrl(jo.get("cans").toString(), ",");
                    ArrayList<String> ans = TypeChange.str2arrl(submitContent.get(i), ",");
                    //cans 是正确答案
                    //ans 是学生提交的答案
                    //ans中 出现 不属于cans 的 ，则0分，否则满分

                    boolean isErr = false;
                    for (String an : ans) {
                        if (!cans.contains(an)) {
                            score = 0f;
                            isErr = true;
                            break;
                        } else {
                            score = qscore;
                        }
                    }
                    if(ans.size() == 0){
                        isErr = true;
                        score = 0f;
                    }
                    if(!isErr){
                        if (ans.size() == cans.size()) {
                            score = qscore;
                        } else {
                            // 拿一半分
                            score = Float.valueOf(String.valueOf(qscore * 0.5));
                        }
                    }
                    readOver.set(i,String.format("%.2f", score));
                }
                // 填空题
                else if (jo.get("qtype").equals(Code.QueType_fillin_question)) {
                    try{
                        String ans = submitContent.get(i).replaceAll("&douhao;", ",");
                        String cans = jo.getString("cans");
                        if(cans.equals(ans)){
                            readOver.set(i, String.valueOf(qscore));
                        }else{
                            readOver.set(i, "0.0");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                //简答题
                else if (jo.get("qtype").equals(Code.QueType_text_question)) {
                    readOver.set(i, "-1");
                } else {
                    System.out.println("Err Cause By autoReadOver: 题目类型不存在: " + jo.get("qtype"));
                }
            }
            int finished = 1;
            Float finial_score = 0F;
            for (String s : readOver) {
                if(s.equals("-1")){
                    finished = 0;
                    break;
                }else{
                    finial_score += Float.parseFloat(s);
                }
            }

            // 计算分率 Rate = 总分/实际总分
            double rate = aWorkDao.selectById(submitWork.getWorkTableId()).getTotalScore() / factTotalScore;
            System.out.println("分率: " + rate);
            if(rate == 0){
                // ERR
                System.out.println("Err Cause By SubmitSeImpl.getRate == 0");
                rate = 1;
            }
            submitWorkContent.setFinishReadOver(finished);
            sw.setScore((float) (finial_score*rate));
            sw.setFinishReadOver(finished);
            submitWorkContent.setReadover(TypeChange.arrL2str(readOver));
            try{

                submitWorkContentDao.updateById(submitWorkContent);
                submitWorkDao.updateById(sw);
                System.out.println("结束AutoReadOver");

                return sw;
            }catch (Exception e){
                System.out.println("结束AutoReadOver");

                return null;
            }
        }else{
            System.out.println("结束AutoReadOver");

            System.out.println("WorkCointent 为 null");
        }
        System.out.println("结束AutoReadOver");

        return sw;
    }
}
