package com.teee.domain.works;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teee.dao.AWorkDao;
import com.teee.dao.BankWorkDao;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import lombok.Data;

import java.util.ArrayList;

@Data
@TableName("submit_work")
public class SubmitWork {
    @TableId(type = IdType.AUTO)
    Integer id;
    Long uid;
    String username;
    Integer workTableId;
    Integer finishReadOver;
    Float score;
    Integer submitId;

    @TableLogic//逻辑删除
    private Integer deleted;

    public SubmitWork(Long uid, String username, Integer workTableId, Integer finishReadOver, Float score, int ans) {
        this.uid = uid;
        this.username = username;
        this.workTableId = workTableId;
        this.finishReadOver = finishReadOver;
        this.score = score;
        this.submitId = ans;
    }

    public SubmitWork() {

    }
    public static int getNumOfQue(SubmitWork sw){
        try{
            AWorkDao aWorkDao = SpringBeanUtil.getBean(AWorkDao.class);
            BankWorkDao bankWorkDao = SpringBeanUtil.getBean(BankWorkDao.class);
            Integer workTableId = sw.getWorkTableId();
            AWork aWork = aWorkDao.selectById(workTableId);
            BankWork bankWork = bankWorkDao.selectById(aWork.getWorkId());
            String questions = bankWork.getQuestions();
            JSONArray jsonArray = TypeChange.str2Jarr(questions);
            return jsonArray.size();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public static JSONArray getWorkCotent(SubmitWork sw){
        try{
            AWorkDao aWorkDao = SpringBeanUtil.getBean(AWorkDao.class);
            BankWorkDao bankWorkDao = SpringBeanUtil.getBean(BankWorkDao.class);
            Integer workTableId = sw.getWorkTableId();
            AWork aWork = aWorkDao.selectById(workTableId);
            BankWork bankWork = bankWorkDao.selectById(aWork.getWorkId());
            String questions = bankWork.getQuestions();
            JSONArray jsonArray = TypeChange.str2Jarr(questions);
            return jsonArray;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
