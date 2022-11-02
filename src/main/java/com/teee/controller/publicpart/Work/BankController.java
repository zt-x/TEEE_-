package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.BankWork;

public interface BankController {
    Result addWorkBank(String token, BankWork bankWork);
    Result getQueBankByTid(String token);
}
