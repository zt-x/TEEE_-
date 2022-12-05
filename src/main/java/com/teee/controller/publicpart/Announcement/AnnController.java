package com.teee.controller.publicpart.Announcement;

import com.teee.domain.returnClass.Result;

public interface AnnController {

    // TODO
    Result releaseNew();
    Result getAllByCid(Integer cid);
    Result getContentByAID(Integer aid);
}
