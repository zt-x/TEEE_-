package com.teee.service.HomeWork.Questions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.BankOwnerDao;
import com.teee.dao.BankQuestionDao;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankOwner;
import com.teee.domain.works.BankQuestion;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Xu ZhengTao
 */
@Service
public class QuestionBankServiceImpl implements QuestionBankService{

    @Autowired
    BankQuestionDao bankQuestionDao;
    @Autowired
    BankOwnerDao bankOwnerDao;
    @Override
    public BooleanReturn createQuestionBank(String bankName, Integer bankType, String questions, Long owner) {
        try{
            BankQuestion bankQuestion = new BankQuestion(bankName, bankType, questions, owner, "[]");
            bankQuestionDao.insert(bankQuestion);
        }catch (Exception e){
            return BooleanReturn.rt(false, "createQuestionBank ERR: " + e.getMessage());
        }
        return BooleanReturn.rt(true);

    }

    @Override
    public BooleanReturn deleteQuestionBank(Integer bankId) {
        try{
            bankQuestionDao.deleteById(bankId);
        }catch(Exception e){
            e.printStackTrace();
            return BooleanReturn.rt(false,"deleteQuestionBank Err: " + e.getMessage());
        }
        return BooleanReturn.rt(true);
    }

    @Override
    public BooleanReturn editQuestionsBank(BankQuestion bankQuestion) {
        try{
            bankQuestionDao.updateById(bankQuestion);
        }catch (Exception e){
            e.printStackTrace();
            return BooleanReturn.rt(false,"deleteQuestionBank Err: " + e.getMessage());
        }
        return BooleanReturn.rt(true);
    }

    @Override
    public BankQuestion getQuestionBankById(Integer baknId) {
        return bankQuestionDao.selectById(baknId);
    }

    @Override
    public BooleanReturn getQuestionBankByOnwer(Long tid) {
        Result res = new Result();
        JSONArray jarr = new JSONArray();
        LambdaQueryWrapper<BankOwner> lqw = new LambdaQueryWrapper<>();
        try{
            BankOwner bankOwner = bankOwnerDao.selectOne(lqw.eq(BankOwner::getOid, tid).eq(BankOwner::getBankType, 1));
            String bids = bankOwner.getBids();
            ArrayList<String> arrayList = TypeChange.str2arrl(bids);
            if(arrayList.size() > 0){
                for (String s : arrayList) {
                    JSONObject o = new JSONObject();
                    BankQuestion bankQuestion = bankQuestionDao.selectById(Integer.valueOf(s));
                    o.put("id", bankQuestion.getBankId());
                    o.put("isPrivate", bankQuestion.getIsPrivate());
                    o.put("bankName", bankQuestion.getBankName());
                    o.put("tags", bankQuestion.getTags());
                    o.put("author", bankQuestion.getOwner());
                    jarr.add(o);
                }
                return new BooleanReturn(true, jarr);
            }else{
                return new BooleanReturn(false, "列表为空");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new BooleanReturn(false, "Err: " + e.getMessage());
        }
    }

    @Override
    public BooleanReturn addBankTags(Integer bankId, ArrayList<String> tags) {
        try{
            BankQuestion bankQuestion = bankQuestionDao.selectById(bankId);
            ArrayList<String> origin = TypeChange.str2arrl(bankQuestion.getTags());
            origin.addAll(tags);
            bankQuestion.setTags(origin.toString());
            bankQuestionDao.updateById(bankQuestion);

        }catch (Exception e){
            return BooleanReturn.rt(false, "addBankTags ERR: " + e.getMessage());
        }
        return BooleanReturn.rt(true);
    }
}
