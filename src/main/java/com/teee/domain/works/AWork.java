package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("work_table")
public class AWork {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer cid;
    Integer workId;
    String workName;
    String deadline;
    @TableField("time_limit")
    Float timeLimit;
    Integer isExam;
    Float totalScore;
    Integer autoReadoverChoice;
    @TableField("auto_readover_fill_in")
    Integer autoReadoverFillIn;
    @TableField("Status")
    Integer Status;

    @TableField(exist = false)
    WorkExamRule rule;

    @TableLogic//逻辑删除
    private Integer deleted;
}
