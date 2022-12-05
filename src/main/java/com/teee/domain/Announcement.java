package com.teee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("announcement")
public class Announcement {
    Integer aid;
    String type;
    String title;
    String releaseTime;
//    String
}
