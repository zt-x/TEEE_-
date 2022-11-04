package com.teee.domain.works;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("submit_work")
public class SubmitWork {
    @TableId(type = IdType.AUTO)
    Integer id;
    Long uid;
    String username;
    Integer workTableId;
    Integer finishReadOver;
    Float score;
    String ans;

    public SubmitWork(Long uid, String username, Integer workTableId, Integer finishReadOver, Float score, String ans) {
        this.uid = uid;
        this.username = username;
        this.workTableId = workTableId;
        this.finishReadOver = finishReadOver;
        this.score = score;
        this.ans = ans;
    }
}
