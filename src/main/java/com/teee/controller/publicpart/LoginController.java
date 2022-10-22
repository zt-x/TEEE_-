package com.teee.controller.publicpart;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.domain.Result;
import com.teee.dao.LoginDao;
import com.teee.domain.LoginData;
import com.teee.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * @author XuzhengTao
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginDao loginDao;


    @PostMapping
    public Result login(@RequestBody LoginData loginData){
        String pwd = DigestUtils.md5DigestAsHex(loginData.getPwd().getBytes(StandardCharsets.UTF_8));
        LambdaQueryWrapper<LoginData> lqw = new LambdaQueryWrapper<>();
        lqw.eq(LoginData::getUid, loginData.getUid());
        LoginData correctData = loginDao.selectOne(lqw);
        if(correctData == null){
            return new Result(Code.Login_Fail_unknown_username,null,"账号不存在");
        }else{
            if(correctData.getPwd().equals(pwd)){
                String token = JWT.jwtEncrypt(correctData.getUid(), correctData.getRole());
                return new Result(Code.Login_Right,token,"登陆成功!");
            }else{
                return new Result(Code.Login_Fail_pwdWrong,null,"密码错误!");

            }
        }
    }

}
