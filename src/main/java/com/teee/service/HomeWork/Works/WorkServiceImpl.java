package com.teee.service.HomeWork.Works;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.BankWorkDao;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.BankWork;
import com.teee.domain.works.SubmitWork;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
@Service
public class WorkServiceImpl implements WorkService{
    @Autowired
    BankWorkDao bankWorkDao;
    @Override
    public BooleanReturn createWorkBank(String workName, String questions, Long owner, Integer isTemp) {
        try {
            BankWork bankWork = new BankWork(workName,questions,owner,"[]", isTemp);
            bankWorkDao.insert(bankWork);
        }catch (Exception e){
            return  BooleanReturn.rt(false, "createWorkBank ERR: " + e.getMessage());
        }
        return BooleanReturn.rt(true);
    }
    @Override
    public BooleanReturn deleteWorkBank(Integer workId) {
        try {
            bankWorkDao.deleteById(workId);
        }catch (Exception e){
            e.printStackTrace();
            return BooleanReturn.rt(false,"deleteWorkBank Err: " + e.getMessage());
        }
        return BooleanReturn.rt(true);
    }
    @Override
    public BooleanReturn editWorksBank(BankWork bankWork) {
        try {
            bankWorkDao.updateById(bankWork);
        }catch (Exception e){
            e.printStackTrace();
            return BooleanReturn.rt(false,"deleteWorkBank Err: " + e.getMessage());

        }
        return BooleanReturn.rt(true);
    }
    @Override
    public BankWork getWorkBankById(Integer workId) {
        return bankWorkDao.selectById(workId);
    }

    @Override
    public List<BankWork> getWorkBankByOnwer(Long owner) {
        LambdaQueryWrapper<BankWork> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BankWork::getOwner, owner);
        return bankWorkDao.selectList(lambdaQueryWrapper);
    }
    @Override
    public BooleanReturn addBankTags(Integer workId, ArrayList<String> tags) {
        try {
            BankWork bankWork = bankWorkDao.selectById(workId);
            ArrayList<String> origin = TypeChange.str_arrl(bankWork.getTags());
            origin.addAll(tags);
            bankWork.setTags(origin.toString());
            bankWorkDao.updateById(bankWork);
        }catch (Exception e){
            e.printStackTrace();
            return BooleanReturn.rt(false,"deleteWorkBank Err: " + e.getMessage());
        }
        return BooleanReturn.rt(true);
    }

    @Override
    public Float readOverWork(BankWork standardBankWork, SubmitWork submitWork) {
        return null;
    }
}
