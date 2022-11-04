package com.teee.service.HomeWork;


import com.teee.dao.SubmitWorkDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.UserInfo;
import com.teee.domain.works.SubmitWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmitServiceImpl implements SubmitService{
    @Autowired
    SubmitWorkDao submitWorkDao;
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

        }catch (Exception e){
            return false;
        }
        return false;
    }

    @Override
    public SubmitWork readOver(SubmitWork submitWork) {
        return null;
    }

    @Override
    public SubmitWork autoReadOver(SubmitWork submitWork, boolean readChoice, boolean readFillIn) {
        return null;
    }
}
