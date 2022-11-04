package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.SubmitWork;

public interface SubmitWorkController {
    Result SubmitWork(SubmitWork submitWork);
    Result getAllSubmitByWorkId(int wid);
    Result setSubmitScore(int subid, float score);
    Result getSubmitSummary(int subid);
}
