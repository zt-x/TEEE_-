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
    Integer isExam;
    Float totalScore;
    Integer autoReadoverChoice;
    @TableField("auto_readover_fillIn")
    Integer autoReadoverFillIn;

    @TableLogic//逻辑删除
    private Integer deleted;
}
