package com.teee.service.HomeWork;

import com.teee.domain.works.SubmitWork;

import java.util.List;

public interface SubmitService {
    List<SubmitWork> getAllSubmitByWorkId(int wid);
    boolean submitWork(SubmitWork submitWork);

    SubmitWork readOver(SubmitWork submitWork);
}
