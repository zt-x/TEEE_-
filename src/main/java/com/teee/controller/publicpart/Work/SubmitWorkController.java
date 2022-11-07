package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.SubmitWork;
import org.springframework.web.bind.annotation.RequestParam;

public interface SubmitWorkController {
    Result SubmitWork(String token, int wid, String ans);
    Result getAllSubmitByWorkId(int wid);
    Result setSubmitScore(int subid, float score);
    Result getSubmitSummary(int subid);
}
