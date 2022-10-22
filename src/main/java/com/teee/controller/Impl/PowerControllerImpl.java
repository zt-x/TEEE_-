package com.teee.controller.Impl;

import com.teee.config.Code;
import com.teee.controller.publicpart.PowerController;
import com.teee.domain.Result;
import com.teee.service.publicpart.Impl.PowerServiceImpl;
import com.teee.utils.SpringBeanUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/power")
public class PowerControllerImpl implements PowerController {
    /*
    * Result:
    *   {
    *       Code:
    *       data:
    *           {
    *               username: username,
    *               role: role,
    *               routers: [
    *                   {name:xxx, }
    *               ]
    *           }
    *       msg:
    *   }
    * */
    @Override
    @PostMapping
    public Result getUser(@RequestHeader("Authorization") String token) {
        Result res = new Result();
        PowerServiceImpl powerService = SpringBeanUtil.getBean(PowerServiceImpl.class);
        if(!powerService.isTokenLegal(token)){
            res.setCode(Code.Token_Illegal);
            res.setData(null);
            res.setMsg("Token 异常");

        }else{
            res.setCode(Code.GetUser_Success);
            res.setData(powerService.getUserData(powerService.getRouter(token), powerService.getUser(token)));
            res.setMsg("获取成功");
        }
        return res;
    }
}
