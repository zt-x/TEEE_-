package com.teee.service.HomeWork.Works;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.dao.AWorkDao;
import com.teee.dao.BankOwnerDao;
import com.teee.dao.BankWorkDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.UserInfo;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.BankOwner;
import com.teee.domain.works.BankWork;
import com.teee.domain.works.SubmitWork;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
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
    @Autowired
    AWorkDao aWorkDao;
    @Override
    public BooleanReturn createWorkBank(BankWork bankWork, Long tid) {
        try {
            bankWork.setOwner(tid);
            bankWorkDao.insert(bankWork);
                // 注册到BankOwner表
            if(bankWork.getIsTemp() == 0){
                try{
                    BankOwner bankOwner = bankOwnerDao.selectOne(new LambdaQueryWrapper<BankOwner>().eq(BankOwner::getOid, tid).eq(BankOwner::getBankType, 0));
                    if(bankOwner != null){
                        String bids = bankOwner.getBids();
                        ArrayList<String> arrayList = TypeChange.str2arrl(bids);
                        arrayList.add(bankWork.getWorkId().toString());
                        bankOwner.setBids(TypeChange.arrL2str(arrayList));
                        bankOwnerDao.updateById(bankOwner);
                    }else{
                        bankOwner = new BankOwner();
                        bankOwner.setOid(tid);
                        bankOwner.setBids("[" + bankWork.getWorkId() + "]");
                        bankOwner.setBankType(0);
                        bankOwnerDao.insert(bankOwner);
                    }
                    return new BooleanReturn(true, bankWork.getWorkId());
                }catch (Exception e){
                    e.printStackTrace();
                    return  BooleanReturn.rt(false, "createWorkBank ERR: " + e.getMessage());
                }
            }else{
                return new BooleanReturn(true, bankWork.getWorkId());
            }
        }catch (Exception e){
            return  BooleanReturn.rt(false, "createWorkBank ERR: " + e.getMessage());
        }
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
    /**
     * return{
     *     bankName:
     *     isPrivate:
     *     (作者)author:
     *     usedTiems:
     *     Tags:[]
     *     numOfQue:[0,0,0]
     *
     * }
     * */
    public BooleanReturn getWorkBankContent(Long tid, Integer wbid) {
        UserInfoDao userInfoDao = SpringBeanUtil.getBean(UserInfoDao.class);
        BankWork bankWork = bankWorkDao.selectById(wbid);
        if(bankWork == null){
            return new BooleanReturn(false,"id=" + wbid + "的作业库不存在");
        }else {
            JSONObject jsonObject = new JSONObject();
            UserInfo userInfo = userInfoDao.selectById(bankWork.getOwner());
            Long author = userInfo.getUid();
            jsonObject.put("BankName", bankWork.getWorkName());
            jsonObject.put("author", userInfo.getUsername());
            jsonObject.put("isPrivate", bankWork.getIsPrivate());
            jsonObject.put("tags", bankWork.getTags());
            jsonObject.put("isMine", author.equals(tid) ?1:0);
            // 统计选择题个数
            JSONArray workCotent = SubmitWork.getWorkCotentByWBID(wbid);
            JSONObject jo;
            int count_choice = 0;
            int count_fillin = 0;
            int count_text = 0;

            if (workCotent != null) {
                for (int i=0;i<workCotent.size();i++) {
                    jo = (JSONObject) workCotent.get(i);
                    if (jo.get("qtype").equals(Code.QueType_choice_question)){
                        count_choice++;
                    }else if(jo.get("qtype").equals(Code.QueType_fillin_question)){
                        count_fillin++;
                    } else if (jo.get("qtype").equals(Code.QueType_text_question)) {
                        count_text++;
                    }
                }
            }
            jsonObject.put("numOfQue","[\"" + count_choice +"\",\"" + count_fillin + "\",\"" + count_text+"\"]");
            int usedTimes = 0;
            usedTimes = aWorkDao.selectCount(new LambdaQueryWrapper<AWork>().eq(AWork::getWorkId, wbid));
            jsonObject.put("usedTimes", usedTimes);
            return new BooleanReturn(true,jsonObject);
        }
    }

    @Override
    public BooleanReturn getWorkBankByOnwer(Long tid) {
        Result res = new Result();
        JSONArray jarr = new JSONArray();
        LambdaQueryWrapper<BankOwner> lqw = new LambdaQueryWrapper<>();
        try{
            BankOwner bankOwner = bankOwnerDao.selectOne(lqw.eq(BankOwner::getOid, tid).eq(BankOwner::getBankType, 0));
            if(bankOwner == null){
                return BooleanReturn.rt(false,"BankOwner不存在, tid=" + tid);
            }
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
                        o.put("author", bankWork.getOwner());
                        jarr.add(o);
                    }
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
//
}
