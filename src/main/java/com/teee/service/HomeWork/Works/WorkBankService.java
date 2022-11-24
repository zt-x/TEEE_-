package com.teee.service.HomeWork.Works;

import com.alibaba.fastjson.JSONArray;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankWork;
import com.teee.domain.works.SubmitWork;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
public interface WorkBankService {
    BooleanReturn createWorkBank(BankWork bankWork, Long tid);
    BooleanReturn deleteWorkBank(Integer work_id);
    BooleanReturn editWorksBank(BankWork bankWork);
    BankWork getWorkBankById(Integer work_id);
    BooleanReturn getWorkBankByOnwer(Long owner);
    BooleanReturn addBankTags(Integer workId, ArrayList<String> tags);
    // 批改作业，返回得分
    Float readOverWork(BankWork standardBankWork, SubmitWork submitWork);
}
