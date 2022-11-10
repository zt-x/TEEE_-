package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
    Integer autoReadoverFillIn;

    @TableLogic//逻辑删除
    private Integer deleted;
}
