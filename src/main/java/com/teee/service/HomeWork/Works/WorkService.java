package com.teee.service.HomeWork.Works;

import com.teee.domain.BooleanReturn;
import com.teee.domain.works.BankWork;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
public interface WorkService {
    BooleanReturn createWorkBank();
    BooleanReturn deleteWorkBank();
    BooleanReturn editWorksBank();
    BankWork getWorkBankById();
    List<BankWork> getWorkBankByOnwer();
    BooleanReturn addBankTags();
}
