package com.teee.service.HomeWork.Works;

import com.teee.domain.BooleanReturn;
import com.teee.domain.works.BankWork;

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
}
