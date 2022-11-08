package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Xu ZhengTao
 */
@Data
@TableName("submit_work_content")
public class SubmitWorkContent {
    @TableId(type = IdType.AUTO)
    Integer submitId;
    /** ["","",""]*/
    String submitContent;

    /** [{qscore:, qreadover:}]**/
    String readover;
    Integer finishReadOver;
}
