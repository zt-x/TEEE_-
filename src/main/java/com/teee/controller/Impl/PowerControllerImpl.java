package com.teee.controller.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teee.config.Code;
import com.teee.controller.PowerController;
import com.teee.dao.UserFaceDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.UserFace;
import com.teee.domain.UserInfo;
import com.teee.domain.returnClass.Result;
import com.teee.service.publicpart.Impl.PowerServiceImpl;
import com.teee.utils.JWT;
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

    @Override
    @RequestMapping("/getInfo")
    @ResponseBody
    public Result getInfo(@RequestHeader("Authorization") String token){
        try{
            UserInfoDao bean = SpringBeanUtil.getBean(UserInfoDao.class);
            UserFaceDao bean1 = SpringBeanUtil.getBean(UserFaceDao.class);
            UserInfo userInfo = bean.selectById(JWT.getUid(token));
            UserFace userFace = bean1.selectById(userInfo.getUid());
            if(userInfo == null){
                return new Result(Code.ERR,null,"获取用户信息失败!");
            }else{
                JSONObject o = (JSONObject) JSONObject.toJSON(userInfo);
                if(userFace == null){
                    o.put("face", "no");
                }else if(userFace.getFaceSrc() == null || userFace.getFaceSrc().equals("")){
                    o.put("face", "no");
                }else{
                    o.put("face", "yes");
                }
                return new Result(Code.Suc, o.toJSONString(), "获取成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Code.ERR,e.getMessage(),e.getMessage());
        }

    }

}
