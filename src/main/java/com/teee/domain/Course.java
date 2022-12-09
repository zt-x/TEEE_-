package com.teee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



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
    String banner;

    @TableField("start_time")
    String startTime;

    @TableField("end_time")
    String endTime;

    Integer status;

}
