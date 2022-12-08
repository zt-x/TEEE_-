package com.teee.domain.returnClass;

import lombok.Data;

@Data
public class BooleanReturn {
    boolean success;
    String msg;
    Object data;
    public BooleanReturn(boolean bool, String msg) {
        this.success = bool;
        this.msg = msg;
    }

    public BooleanReturn(boolean bool, Object data) {
        this.success = bool;
        this.data = data;
    }

    public BooleanReturn(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public BooleanReturn(boolean bool) {
        this.success = bool;
    }

    public static BooleanReturn rt(boolean bool, String msg){
        return new BooleanReturn(bool, msg);
    }
    public static BooleanReturn rt(boolean bool){
        return new BooleanReturn(bool);
    }

    @Override
    public String toString() {
        return "BooleanReturn{" +
                "bool=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
