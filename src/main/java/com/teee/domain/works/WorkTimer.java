package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Xu ZhengTao
 */
@Data
@TableName("work_timer")
public class WorkTimer {
    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField("restTime")
    String restTime;

    @TableField("uid")
    Long uid;

    @TableField("wid")
    Integer wid;
}
