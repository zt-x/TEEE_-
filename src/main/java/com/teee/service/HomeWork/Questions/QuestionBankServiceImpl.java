package com.teee.service.HomeWork.Questions;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.BankQuestionDao;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.BankQuestion;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
@Service
public class QuestionBankServiceImpl implements QuestionBankService{

    @Autowired
    BankQuestionDao bankQuestionDao;

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
    public List<BankQuestion> getQuestionBankByOnwer(Long tid) {
        LambdaQueryWrapper<BankQuestion> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BankQuestion::getOwner, tid);
        return bankQuestionDao.selectList(lambdaQueryWrapper);
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
