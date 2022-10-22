package com.teee.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Xu zhengTao
 */
@Data
@TableName("user_login")
public class LoginData {
    @TableId
    Long uid;
    String pwd;
    // role可选 teacher/ student/ admin
    String role;

    public LoginData(Long uid, String pwd, String role) {
        this.uid = uid;
        this.pwd = pwd;
        this.role = role;
    }
}

