package com.teee.service.HomeWork;


import com.teee.dao.AWorkDao;
import com.teee.dao.SubmitWorkContentDao;
import com.teee.dao.SubmitWorkDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.UserInfo;
import com.teee.domain.works.AWork;
import com.teee.domain.works.SubmitWork;
import com.teee.domain.works.SubmitWorkContent;
import com.teee.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    //TODO
    public static SubmitWork autoReadOver(SubmitWork submitWork, boolean readChoice, boolean readFillIn) {
        SubmitWorkContentDao submitWorkContentDao = SpringBeanUtil.getBean(SubmitWorkContentDao.class);
        SubmitWork sw = submitWork;
        Integer submitId = sw.getSubmitId();
        String submitContent = submitWorkContentDao.selectById(submitId).getSubmitContent();
        return submitWork;
    }
}
