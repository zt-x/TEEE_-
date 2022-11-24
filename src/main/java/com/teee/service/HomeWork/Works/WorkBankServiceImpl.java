package com.teee.service.HomeWork.Works;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.teee.config.Code;
import com.teee.dao.BankOwnerDao;
import com.teee.dao.BankWorkDao;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankOwner;
import com.teee.domain.works.BankWork;
import com.teee.domain.works.SubmitWork;
import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
@Service
public class WorkBankServiceImpl implements WorkBankService {
    @Autowired
    BankWorkDao bankWorkDao;
    @Autowired
    BankOwnerDao bankOwnerDao;
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
    public BooleanReturn getWorkBankByOnwer(Long tid) {
        Result res = new Result();
        JSONArray jarr = new JSONArray();
        LambdaQueryWrapper<BankOwner> lqw = new LambdaQueryWrapper<>();
        try{
            BankOwner bankOwner = bankOwnerDao.selectOne(lqw.eq(BankOwner::getOid, tid).eq(BankOwner::getBankType, 0));
            String bids = bankOwner.getBids();
            ArrayList<String> arrayList = TypeChange.str2arrl(bids);
            if(arrayList.size() > 0){
                for (String s : arrayList) {
                    JSONObject o = new JSONObject();
                    BankWork bankWork = bankWorkDao.selectById(Integer.valueOf(s));
                    if(bankWork.getIsTemp() == 1){
                        continue;
                    }else {
                        o.put("id", bankWork.getWorkId());
                        o.put("isPrivate", bankWork.getIsPrivate());
                        o.put("bankName", bankWork.getWorkName());
                        o.put("tags", bankWork.getTags());
                        jarr.add(o);
                    }
                }
                return new BooleanReturn(true, jarr);
            }else{
                return new BooleanReturn(false, "列表为空");
            }
        }catch(Exception e){
            return new BooleanReturn(false, "Err: " + e.getMessage());
        }
    }
    @Override
    public BooleanReturn addBankTags(Integer workId, ArrayList<String> tags) {
        try {
            BankWork bankWork = bankWorkDao.selectById(workId);
            ArrayList<String> origin = TypeChange.str2arrl(bankWork.getTags());
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
