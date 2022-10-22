package com.teee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_info")
@Data
public class UserInfo {
    @TableId
    Long uid;
    @TableField(exist = false)
    String role;
    String username;
    String avatar;

    public UserInfo(Long uid, String role, String username, String avatar) {
        this.uid = uid;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }
    public UserInfo(Long uid, String username, String avatar) {
        this.uid = uid;
        this.username = username;
        this.avatar = avatar;
    }
    public UserInfo(){

    }
}
