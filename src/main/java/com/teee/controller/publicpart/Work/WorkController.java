package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;

/**
 * @author Xu ZhengTao
 */
public interface WorkController {
    Result getAllWorksByCID(int cid);
    Result ReleaseAWork(AWork aWork);
}
