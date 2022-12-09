package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import com.teee.domain.works.WorkExamRule;

/**
 * @author Xu ZhengTao
 */
public interface WorkController {
    // public
    Result getAllWorksByCID(int cid);
    Result ReleaseAWork(AWork aWork);
    Result getWork(int id);
    Result deleteAWork(Integer wid);
    Result getWorkFinishStatus(String token, Integer cid);
    Result getWorkTimer(String token, Integer wid);
    Result setRules(WorkExamRule workExamRule);
    Result getExamRulePre(Integer wid);
    Result getExamRuleEnter(Integer wid);

}
