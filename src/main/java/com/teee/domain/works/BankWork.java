package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teee.domain.works.QuestionObject.QuestionObject;
import lombok.Data;
import java.util.ArrayList;

/**
 * @author Xu ZhengTao
 * @data: questions: JSONArray<JSONObject>.toString();
 */
@Data
@TableName("bank_work")
public class BankWork {
    @TableId(value="work_id", type = IdType.AUTO)
    private int workId;
    private String questions;
    private String workName;
    private Long owner;
    private String tags;

    public BankWork(String workName) {
        this.workName = workName;
    }

    public BankWork(String questions, String workName) {
        this.questions = questions;
        this.workName = workName;
    }

    public BankWork(String workName, Long owner) {
        this.workName = workName;
        this.owner = owner;
    }
}
