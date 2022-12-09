package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankQuestion;
import com.teee.domain.works.BankWork;

public interface BankController {
    //作业库
    Result addWorkBank(String token, BankWork bankWork);
    Result getWorkBankByTid(String token);
    Result getWorkBankContentByID(String token, Integer wbid);
    Result editWorkBank(BankWork bankWork);
    Result deleteWorkBank(Integer wbid);

    //题库
    Result addQueBank(String token, BankQuestion bankQuestion);
    Result getQueBankByTid(String token);
    Result editQueBank(BankQuestion bankQuestion);
    Result deleteQueBank(Integer qbid);
}
