package com.teee.controller.publicpart;


import com.teee.config.Code;
import com.teee.dao.UserInfoDao;
import com.teee.domain.returnClass.Result;
import com.teee.dao.LoginDao;
import com.teee.domain.LoginData;
import com.teee.domain.UserInfo;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * @author Xu ZhengTao
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    private final LoginDao loginDao;
    private final UserInfoDao userInfoDao;

    public RegisterController(LoginDao loginDao, UserInfoDao userInfoDao) {
        this.loginDao = loginDao;
        this.userInfoDao = userInfoDao;
    }

    @PostMapping
    public Result register(@RequestBody LoginData loginData){

        // 查询用户是否存在
        if (loginDao.selectById(loginData.getUid()) != null) {
            return new Result(Code.Register_Fail_usernameExist, null, "该用户已存在");
        }
        int insertResult = loginDao.insert(
            new LoginData(
                loginData.getUid(),
                DigestUtils.md5DigestAsHex(loginData.getPwd().getBytes(StandardCharsets.UTF_8)),
                loginData.getRole()
            )
        );
        int insertResult2 = userInfoDao.insert(
            new UserInfo(
                loginData.getUid(),
                "",
                ""
            )
        );
        return new Result(Code.Register_Right, insertResult, "注册成功了应该");
    }

    @PutMapping
    public Result setUserInfo(@RequestBody UserInfo userInfo){

        int i = userInfoDao.updateById(userInfo);
        if(i == 1){
            return new Result(Code.SetUserInfo_Success, null, "设置成功");
        }else{
            return new Result(Code.SetUserInfo_Fail, null, "设置失败");
        }
    }
}
