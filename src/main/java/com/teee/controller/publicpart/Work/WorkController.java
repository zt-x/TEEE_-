package com.teee.controller.publicpart.Work;

import com.teee.domain.returnClass.Result;
import com.teee.domain.works.AWork;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Xu ZhengTao
 */
public interface WorkController {
    // public
    Result getAllWorksByCID(int cid);
    Result ReleaseAWork(AWork aWork);
    Result getWork(int id);
    Result deleteAWork(Integer wid);
}
