package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Xu ZhengTao
 */
@Data
@TableName("work_exam_track")
public class WorkExamTrack {
    @TableId
    Long uid;

    @TableId
    Integer wid;

    String faceCheck;

    String ipAddress;

    String enterTime;

    Integer closeTimes;

    String flashPhotos;

    @TableLogic//逻辑删除
    private Integer deleted;
}
