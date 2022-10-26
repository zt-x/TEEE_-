package com.teee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teee.dao.UserInfoDao;
import com.teee.utils.SpringBeanUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;



@Data
@TableName("course_table")
public class Course {

    @TableId(value = "id", type= IdType.AUTO)
    Integer id;

    @TableField("name")
    String courseName;

    @TableField("teacherID")
    Long tid;

    String college;
    // banner为图片URL
    String banner;

    @TableField("start_time")
    String startTime;

    @TableField("end_time")
    String endTime;

    Integer status;

}
