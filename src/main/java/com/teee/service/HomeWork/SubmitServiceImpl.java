package com.teee.service.HomeWork;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.teee.config.Code;
import com.teee.dao.*;
import com.teee.domain.UserInfo;
import com.teee.domain.works.SubmitWork;
import com.teee.domain.works.SubmitWorkContent;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmitServiceImpl implements SubmitService{
    @Autowired
    SubmitWorkDao submitWorkDao;
    @Autowired
    SubmitWorkContentDao submitWorkContentDao;
    @Autowired
    AWorkDao aWorkDao;
    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public List<SubmitWork> getAllSubmitByWorkId(int wid) {
        return null;
    }

    @Override
    public boolean submitWork(SubmitWork submitWork) {
        try {
            Long uid = submitWork.getUid();
            UserInfo userInfo = userInfoDao.selectById(uid);
            submitWork.setUsername(userInfo.getUsername());
            submitWorkDao.insert(submitWork);
            boolean readChoice = (aWorkDao.selectById(submitWork.getWorkTableId()).getAutoReadoverChoice() == 1);
            boolean readFillIn = (aWorkDao.selectById(submitWork.getWorkTableId()).getAutoReadoverFillIn() == 1);
            try{
                SubmitWork submitWorkAutoReadOver = SubmitServiceImpl.autoReadOver(submitWork, readChoice, readFillIn);
                submitWorkDao.updateById(submitWorkAutoReadOver);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public SubmitWork readOver(SubmitWork submitWork) {
        return null;
    }


    public static SubmitWork autoReadOver(SubmitWork submitWork, boolean readChoice, boolean readFillIn) {
        SubmitWorkContentDao submitWorkContentDao = SpringBeanUtil.getBean(SubmitWorkContentDao.class);
        BankWorkDao bankWorkDao = SpringBeanUtil.getBean(BankWorkDao.class);
        SubmitWork sw = submitWork;
        Integer submitId = sw.getSubmitId();
        SubmitWorkContent submitWorkContent = submitWorkContentDao.selectById(submitId);
        ArrayList<String> readOver;
        ArrayList<String> submitContent = TypeChange.str2arrl(submitWorkContent.getSubmitContent());
        System.out.println("subContent = " + submitContent);
        String readover = submitWorkContent.getReadover();
        if(readover.equals("")){
            int len = SubmitWork.getNumOfQue(sw);
            readOver = new ArrayList<>(len);
            for(int i=0; i<len; i++){
                readOver.add(i,"");
            }
            System.out.println(readOver);
        }else {
            readOver = TypeChange.str2arrl(readover);
        }
        System.out.println("size="+readOver.size());
        JSONArray workCotent = SubmitWork.getWorkCotent(sw);
        JSONObject jo;
        if (workCotent != null) {
            for (int i=0;i<workCotent.size();i++) {
                jo = (JSONObject) workCotent.get(i);
                Float qscore = Float.valueOf(jo.get("qscore").toString());
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
                        } else {
                            score = qscore;
                        }
                    }
                    if(!isErr){
                        if (ans.size() == cans.size()) {
                            score = qscore;
                        } else {
                            // 拿一半分
                            score = Float.valueOf(String.valueOf(qscore * 0.5));
                        }
                    }
                    readOver.set(i,String.valueOf(score));
                }
                // 填空题
                // TODO 简答题的Code设置出错
                else if (jo.get("qtype").equals(Code.QueType_fillin_question)) {
                    try{
                        String ans = submitContent.get(i);
                        String cans = jo.getString("cans");
                        System.out.println("自动批改填空题中 ... ");
                        System.out.println("ans: " + ans);
                        System.out.println("cans: " + cans);
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
            submitWorkContent.setFinishReadOver(finished);
            sw.setScore(finial_score);
            sw.setFinishReadOver(finished);
            submitWorkContent.setReadover(TypeChange.arrL2str(readOver));
            try{
                submitWorkContentDao.updateById(submitWorkContent);
                return sw;
            }catch (Exception e){
                return null;
            }
        }else{
            System.out.println("WorkCointent 为 null");
        }
        return sw;
    }
}
