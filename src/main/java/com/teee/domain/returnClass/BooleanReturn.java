package com.teee.domain.returnClass;

import lombok.Data;

@Data
public class BooleanReturn {
    boolean bool;
    String msg;

    public BooleanReturn(boolean bool, String msg) {
        this.bool = bool;
        this.msg = msg;
    }

    public BooleanReturn(boolean bool) {
        this.bool = bool;
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
                "bool=" + bool +
                ", msg='" + msg + '\'' +
                '}';
    }
}
