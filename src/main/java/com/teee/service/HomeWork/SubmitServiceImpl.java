package com.teee.service.HomeWork;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.controller.publicpart.Work.Impl.AutoReadOver;
import com.teee.dao.*;
import com.teee.domain.UserInfo;
import com.teee.domain.works.SubmitWork;
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
    @Autowired
    AutoReadOver autoReadOver;

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
            submitWorkDao.delete(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getUid, submitWork.getUid()).eq(SubmitWork::getWorkTableId, submitWork.getWorkTableId()));
            submitWorkDao.insert(submitWork);
            boolean readChoice = (aWorkDao.selectById(submitWork.getWorkTableId()).getAutoReadoverChoice() == 1);
            boolean readFillIn = (aWorkDao.selectById(submitWork.getWorkTableId()).getAutoReadoverFillIn() == 1);
            try{
                System.out.println(111);
                autoReadOver.autoReadOver(submitWork, readChoice, readFillIn);
                System.out.println(222);

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



}
