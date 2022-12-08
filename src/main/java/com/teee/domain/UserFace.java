package com.teee.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_face")
@Data
public class UserFace {
    Long uid;
    String faceSrc;

    public UserFace(Long uid, String faceSrc) {
        this.uid = uid;
        this.faceSrc = faceSrc;
    }

    public UserFace() {
    }
}
