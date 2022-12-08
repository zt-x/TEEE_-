package com.teee.domain.works;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Xu ZhengTao
 * rule:
 * [ FACECHECK ]
 */
@Data
@TableName("work_exam_rule")
public class WorkExamRule {
    @TableId
    Integer wid;

    String rulePre;

    String ruleEnter;

    @TableLogic//逻辑删除
    private Integer deleted;
}
