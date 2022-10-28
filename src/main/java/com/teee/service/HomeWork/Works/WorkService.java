package com.teee.service.HomeWork.Works;

import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.BankWork;
import com.teee.domain.works.SubmitWork;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
public interface WorkService {
    BooleanReturn createWorkBank(String workName,String questions,Long owner);
    BooleanReturn deleteWorkBank(Integer work_id);
    BooleanReturn editWorksBank(BankWork bankWork);
    BankWork getWorkBankById(Integer work_id);
    List<BankWork> getWorkBankByOnwer(Long owner);
    BooleanReturn addBankTags(Integer workId, ArrayList<String> tags);
    // 批改作业，返回得分
    Float readOverWork(BankWork standardBankWork, SubmitWork submitWork);
}
