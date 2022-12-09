package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;

public interface SubmitWorkController {
    Result SubmitWork(String token, int wid, String ans, String files);
    Result getAllSubmitByWorkId(int wid);
    Result setSubmitScore(int subid, String score);
    Result getSubmitSummary(int subid);
    Result getSubmitBySid(int sid);
    Result getSubmitByWorkId(String token, int wid);
}
