package com.teee.controller;

import com.teee.domain.returnClass.Result;
import org.springframework.web.bind.annotation.RequestHeader;

public interface PowerController {

    Result getUser(String token);
    Result getRole(String token);
    Result getInfo(String token);
}
