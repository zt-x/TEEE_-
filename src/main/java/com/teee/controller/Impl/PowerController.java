package com.teee.controller.Impl;

import com.teee.domain.returnClass.Result;

public interface PowerController {

    Result getUser(String token);
    Result getRole(String token);
}
