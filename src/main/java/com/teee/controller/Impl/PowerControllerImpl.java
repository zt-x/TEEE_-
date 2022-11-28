package com.teee.controller.Impl;

import com.teee.config.Code;
import com.teee.domain.UserInfo;
import com.teee.domain.returnClass.Result;
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

    @Override
    @GetMapping
    public Result getRole(@RequestHeader("Authorization") String token) {
        Result res = new Result();
        PowerServiceImpl powerService = SpringBeanUtil.getBean(PowerServiceImpl.class);
        try{
            UserInfo user = powerService.getUser(token);
            if(user != null){
                res.setCode(Code.Suc);
                res.setMsg("获取成功");
                res.setData(user.getRole());
            }else{
                res.setCode(Code.ERR);
                res.setMsg("获取失败，用户不存在");
                res.setData(null);
            }
        }catch(Exception e){
            res.setCode(Code.ERR);
            res.setMsg("获取失败 Err cause by PowerController.getRole: " + e.getMessage());
            res.setData(null);
        }

        return res;
    }
}
