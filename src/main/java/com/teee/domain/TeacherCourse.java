package com.teee.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher_course")
public class TeacherCourse {
    Long tid;
    int cid;
}
