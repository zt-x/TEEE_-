package com.teee.domain.works;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bank_owner")
public class BankOwner {
    @TableId("id")
    Integer id;
    @TableField("owner_id")
    Long oid;
    @TableField("bids")
    String bids;

    @TableField("bank_type")
    Integer bankType;
}
