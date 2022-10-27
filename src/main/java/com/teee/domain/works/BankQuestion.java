package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teee.domain.works.QuestionObject.QuestionObject;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author Xu ZhengTao
 * @data{
 *  bankType: 题库类型，1、选择题 2、填空题 3、简答题
 *  questions: JSONArray
 * }
 */
@Data
@TableName("bank_question")
public class BankQuestion {
    @TableId(type = IdType.AUTO)
    private Integer bankId;
    private String bankName;
    private Integer bankType;
    private String questions;
}
